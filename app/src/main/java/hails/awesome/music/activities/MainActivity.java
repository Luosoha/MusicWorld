package hails.awesome.music.activities;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.thin.downloadmanager.DefaultRetryPolicy;
import com.thin.downloadmanager.DownloadRequest;
import com.thin.downloadmanager.DownloadStatusListenerV1;
import com.thin.downloadmanager.ThinDownloadManager;

import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import hails.awesome.music.R;
import hails.awesome.music.callbacks.OnBackFromMainPlayerListener;
import hails.awesome.music.callbacks.OnMusicPlayerActionListener;
import hails.awesome.music.callbacks.OnSongReadyListener;
import hails.awesome.music.managers.PlayerManager;
import hails.awesome.music.managers.RetrofitContext;
import hails.awesome.music.managers.SQLiteHelper;
import hails.awesome.music.networks.models.SearchSongResponseBody;
import hails.awesome.music.networks.models.Song;
import hails.awesome.music.screens.mainplayer.MainPlayerFragment;
import hails.awesome.music.screens.mainplayer.MainPlayerPresenter;
import hails.awesome.music.screens.viewpager.ViewPagerPresenter;
import hails.awesome.music.utils.ActionHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity
        implements FragmentManager.OnBackStackChangedListener, OnBackFromMainPlayerListener, OnMusicPlayerActionListener {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.rl_mini_player)
  RelativeLayout miniPlayerLayout;
  @BindView(R.id.iv_download_song)
  ImageView downloadSongIv;
  @BindView(R.id.iv_share_song)
  ImageView shareSongIv;
  @BindView(R.id.ll_main_player_toolbar)
  LinearLayout mainPlayerToolbar;
  @BindView(R.id.sb_progress)
  SeekBar progressSb;
  @BindView(R.id.tv_song_name_inside_tool_bar)
  TextView songNameInsideToolBarTv;
  @BindView(R.id.tv_song_artist_inside_tool_bar)
  TextView songArtistInsideToolBarTv;
  @BindView(R.id.civ_top_song)
  CircleImageView songImageCiv;
  @BindView(R.id.tv_top_song_name)
  TextView songNameTv;
  @BindView(R.id.tv_top_song_artist)
  TextView songArtistTv;
  @BindView(R.id.fab_action)
  FloatingActionButton actionFab;

  private SQLiteHelper sqLiteHelper;
  private PlayerManager playerManager;
  private Handler handler = new Handler();
  private Runnable runnable;
  private OnSongReadyListener onSongReadyListener;
  private ThinDownloadManager downloadManager;
  private NotificationCompat.Builder builder;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setSupportActionBar(toolbar);
    PlayerManager.init(this, this);
    playerManager = PlayerManager.getInstance();
    builder = new NotificationCompat.Builder(this)
            .setSmallIcon(R.mipmap.ic_app)
            .setContentTitle(getString(R.string.app_name))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true);
  }

  @Override
  protected void onStart() {
    super.onStart();
    downloadManager = new ThinDownloadManager();
    sqLiteHelper = new SQLiteHelper(this);
    pushView(R.id.fl_container, new ViewPagerPresenter().getFragment(), false);
    setupSeekBarListener();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  private void setupSeekBarListener() {
    progressSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(runnable);
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        int currentPosition = progressSb.getProgress() * playerManager.getSongDuration() / progressSb.getMax();
        playerManager.seekTo(currentPosition);
        handler.postDelayed(runnable, 100);
      }
    });
  }

  @OnClick(R.id.fab_action)
  public void onFabActionClick() {
    if (playerManager.isPlaying()) {
      actionFab.setImageResource(R.drawable.ic_play_arrow_white_24px);
      playerManager.setIsPlaying(false);
    } else {
      actionFab.setImageResource(R.drawable.ic_pause_white_24px);
      playerManager.setIsPlaying(true);
    }
  }

  @OnClick(R.id.rl_mini_player)
  public void onMiniPlayerClick() {
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    miniPlayerLayout.setVisibility(View.GONE);

    mainPlayerToolbar.setVisibility(View.VISIBLE);
    songNameInsideToolBarTv.setText(playerManager.getCurrentSong().getName());
    songArtistInsideToolBarTv.setText(playerManager.getCurrentSong().getArtist());

    MainPlayerFragment mainPlayerFragment = (MainPlayerFragment) new MainPlayerPresenter().getFragment();
    mainPlayerFragment.setOnBackFromMainPlayerListener(this);
    mainPlayerFragment.setOnMusicPlayerActionListener(this);
    onSongReadyListener = mainPlayerFragment;

    pushView(R.id.fl_container, mainPlayerFragment, true);
  }

  @OnClick(R.id.iv_back_from_main_player)
  public void onBackFromMainPlayerEvent() {
    getSupportActionBar().hide();
    onBackPressed();
  }

  @OnClick(R.id.iv_download_song)
  public void onDownloadSong() {
    Toast.makeText(this, "Downloading " + playerManager.getCurrentSong().getName(), Toast.LENGTH_SHORT).show();
    Uri downloadUri = Uri.parse(playerManager.getSongUrl());
    String fileName = playerManager.getCurrentSong().getName() + "_" + playerManager.getCurrentSong().getArtist() + ".mp3";
    Uri destinationUri = Uri.parse(getFilesDir().toString() + "/" + fileName);
    final int notifId = new Random().nextInt(10000000);

    final NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    builder.setContentText(playerManager.getCurrentSong().getName())
            .setProgress(100, 0, false);
    manager.notify(notifId, builder.build());

    DownloadRequest downloadRequest = new DownloadRequest(downloadUri)
            .setRetryPolicy(new DefaultRetryPolicy())
            .setDestinationURI(destinationUri).setPriority(DownloadRequest.Priority.HIGH)
            .setStatusListener(new DownloadStatusListenerV1() {
              @Override
              public void onDownloadComplete(DownloadRequest downloadRequest) {
                Toast.makeText(MainActivity.this, "Download completed", Toast.LENGTH_LONG).show();
                builder.setProgress(0, 0, false);
                builder.setContentText("Download " + playerManager.getCurrentSong().getName() + " completed");
                manager.notify(notifId, builder.build());
                playerManager.getCurrentSong().setSubgenres(playerManager.getSubgenres());
                sqLiteHelper.insertSong(playerManager.getCurrentSong());
              }

              @Override
              public void onDownloadFailed(DownloadRequest downloadRequest, int i, String s) {
                Toast.makeText(MainActivity.this, "Download failed", Toast.LENGTH_LONG).show();
                manager.cancel(notifId);
              }

              @Override
              public void onProgress(DownloadRequest downloadRequest, long totalBytes, long downloadedBytes, int progress) {
                builder.setProgress(100, progress, false);
                manager.notify(notifId, builder.build());
              }
            });
    downloadManager.add(downloadRequest);
  }

  @OnClick(R.id.iv_share_song)
  public void onShareSong() {
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, playerManager.getSongUrl());
    sendIntent.setType("text/plain");
    startActivity(sendIntent);
  }

  @Override
  public void onBackPressed() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    if (fragmentManager.getBackStackEntryCount() == 0) {
      finish();
    } else if (fragmentManager.getBackStackEntryCount() == 1) { // back to main screen
      getSupportActionBar().show();
      getSupportFragmentManager().popBackStack();
    } else {
      getSupportActionBar().hide();
      fragmentManager.popBackStack();
    }
  }

  private boolean sentDurationToMainPlayer = false;

  private void startSeekbarProgress() {
    runnable = new Runnable() {
      @Override
      public void run() {
        if (playerManager.getSongDuration() > 0 && playerManager.getCurrentPosition() > 0 && !sentDurationToMainPlayer) {
          if (onSongReadyListener != null) {
            onSongReadyListener.onSongReady();
          }
          sentDurationToMainPlayer = true;
          progressSb.setMax(playerManager.getSongDuration());
        }
        handler.postDelayed(this, 100);
        progressSb.setProgress(playerManager.getCurrentPosition());
      }
    };
  }

  @Override
  public void onBackFromMainPlayer() {
    miniPlayerLayout.setVisibility(View.VISIBLE);
    mainPlayerToolbar.setVisibility(View.GONE);
    getSupportActionBar().setDisplayShowTitleEnabled(true);
  }

  @Override
  protected void onDestroy() {
    if (!playerManager.isNull()) {
      playerManager.release();
    }
    super.onDestroy();
  }

  @Override
  public void onBackStackChanged() {

  }

  @Override
  public void onPlaySong(Song song, String songUrl, boolean doRevealMiniPlayer) {
    if (song != null) {
      if (playerManager.isPlaying()) {
        playerManager.stop();
      }
      playerManager.setCurrentSong(song);
      playerManager.setSongUrl(songUrl);
      playerManager.setIsPlaying(true);
      playerManager.playNewSong(songUrl);

      actionFab.setImageResource(R.drawable.ic_pause_white_24px);
      if (song.getImages() != null) {
        Picasso.with(this).load(song.getIconUrl()).into(songImageCiv);
      } else {
        Picasso.with(this).load(R.mipmap.ic_app).into(songImageCiv);
      }
      songNameTv.setText(song.getName());
      songArtistTv.setText(song.getArtist());
      if (doRevealMiniPlayer) {
        miniPlayerLayout.setVisibility(View.VISIBLE);
      } else {
        sentDurationToMainPlayer = false;
        songNameInsideToolBarTv.setText(playerManager.getCurrentSong().getName());
        songArtistInsideToolBarTv.setText(playerManager.getCurrentSong().getArtist());
      }
      if (playerManager.isOfflineMode()) {
        downloadSongIv.setVisibility(View.INVISIBLE);
        shareSongIv.setVisibility(View.INVISIBLE);
      } else {
        downloadSongIv.setVisibility(View.VISIBLE);
        shareSongIv.setVisibility(View.VISIBLE);
      }

      startSeekbarProgress();
      handler.postDelayed(runnable, 100);
    } else {
      Toast.makeText(this, getString(R.string.song_not_found_message), Toast.LENGTH_SHORT).show();
    }
  }

  @Override
  public void onPauseAction() {
    playerManager.setIsPlaying(false);
    actionFab.setImageResource(R.drawable.ic_play_arrow_white_24px);
  }

  @Override
  public void onResumeAction() {
    playerManager.setIsPlaying(true);
    actionFab.setImageResource(R.drawable.ic_pause_white_24px);
  }

  @Override
  public void onProgressChanged(int progress) {
    playerManager.seekTo(progress);
  }

  @Override
  public void onSongEnded() {
    int position = ActionHelper.findNextSongPositionOf(playerManager.getPlayList(), playerManager.getCurrentSong());
    searchForNextSong(position);
  }

  private void searchForNextSong(int position) {
    showProgress();
    final Song song = playerManager.getPlayList().get(position);
    if (onSongReadyListener != null) {
      if (song.getImages() != null) {
        onSongReadyListener.onNextSongReady(song.getImageUrl());
      } else {
        onSongReadyListener.onNextSongReady(null);
      }
    }
    String keyword = song.getName() + " " + song.getArtist();
    RetrofitContext.getSearchSong(keyword).enqueue(new Callback<SearchSongResponseBody>() {
      @Override
      public void onResponse(Call<SearchSongResponseBody> call, Response<SearchSongResponseBody> response) {
        SearchSongResponseBody songs = response.body();
        if (songs != null && !songs.getSongs().isEmpty()) {
          onPlaySong(song, songs.getSongUrl(), miniPlayerLayout.isShown());
        } else {
          Toast.makeText(MainActivity.this, getString(R.string.song_not_found_message), Toast.LENGTH_SHORT).show();
        }
        hideProgress();
      }

      @Override
      public void onFailure(Call<SearchSongResponseBody> call, Throwable t) {
        hideProgress();
      }
    });
  }

}

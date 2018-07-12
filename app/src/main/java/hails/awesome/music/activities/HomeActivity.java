package hails.awesome.music.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
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

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
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
import hails.awesome.music.screens.viewpager.ViewPagerFragment;
import hails.awesome.music.screens.viewpager.ViewPagerPresenter;
import hails.awesome.music.utils.ActionHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends BaseActivity
        implements FragmentManager.OnBackStackChangedListener, OnBackFromMainPlayerListener, OnMusicPlayerActionListener {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.rl_mini_player)
  RelativeLayout miniPlayerLayout;
  @BindView(R.id.iv_download_song)
  ImageView downloadSongIv;
  @BindView(R.id.iv_share_song)
  ImageView shareSongIv;
  @BindView(R.id.iv_ringtone)
  ImageView ringtoneIv;
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

  private static final int WRITE_SETTINGS_REQUEST_CODE = 2009;

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
    new ViewPagerPresenter(this).pushView(R.id.fl_container, false);
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

    MainPlayerFragment mainPlayerFragment = (MainPlayerFragment) new MainPlayerPresenter(this).getFragment();
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
    if (sqLiteHelper.isDownloaded(playerManager.getCurrentSong())) {
      Toast.makeText(this, "You've already downloaded this song!", Toast.LENGTH_SHORT).show();
      return;
    }

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
                Toast.makeText(HomeActivity.this, "Download completed", Toast.LENGTH_LONG).show();
                builder.setProgress(0, 0, false);
                builder.setContentText("Download " + playerManager.getCurrentSong().getName() + " completed");
                manager.notify(notifId, builder.build());
                playerManager.getCurrentSong().setSubgenres(playerManager.getSubgenres());
                sqLiteHelper.insertSong(playerManager.getCurrentSong());
              }

              @Override
              public void onDownloadFailed(DownloadRequest downloadRequest, int i, String s) {
                Toast.makeText(HomeActivity.this, "Download failed", Toast.LENGTH_LONG).show();
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

  @SuppressLint("NewApi")
  @OnClick(R.id.iv_ringtone)
  public void onRingtoneClick() {
//    Cursor cursor = null;
//    try {
//      String[] proj = { MediaStore.Images.Media.DATA };
//      cursor = getContentResolver().query(Uri.parse("content://media/internal/audio/media/465"), proj, null, null, null);
//      int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//      cursor.moveToFirst();
//      cursor.getString(column_index);
//    } finally {
//      if (cursor != null) {
//        cursor.close();
//      }
//    }

    if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED) {
      final AlertDialog.Builder builder = new AlertDialog.Builder(this);
      builder.setTitle(getString(R.string.setting_ringtone_title));
      builder.setMessage(getString(R.string.setting_ringtone_message));
      builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
          settingNewRingtone();
        }
      });
      builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {

        }
      });
      builder.show();

    } else {
      requestPermissions(new String[] {Manifest.permission.WRITE_SETTINGS}, WRITE_SETTINGS_REQUEST_CODE);
    }
  }

  private void settingNewRingtone() {
    String fileName = playerManager.getCurrentSong().getName() + "_" + playerManager.getCurrentSong().getArtist() + ".mp3";
    File k = new File(getFilesDir(), fileName);

    ContentValues values = new ContentValues();
    values.put(MediaStore.MediaColumns.DATA, k.getAbsolutePath());
    values.put(MediaStore.MediaColumns.TITLE, playerManager.getCurrentSong().getName());
    values.put(MediaStore.MediaColumns.SIZE, k.length());
    values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");
    values.put(MediaStore.Audio.Media.ARTIST, playerManager.getCurrentSong().getArtist());
    values.put(MediaStore.Audio.Media.DURATION, playerManager.getSongDuration());
    values.put(MediaStore.Audio.Media.IS_RINGTONE, true);
    values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);
    values.put(MediaStore.Audio.Media.IS_ALARM, false);
    values.put(MediaStore.Audio.Media.IS_MUSIC, false);

    Uri uri = MediaStore.Audio.Media.getContentUriForPath(k.getAbsolutePath());
    getContentResolver().delete(
            uri, MediaStore.MediaColumns.DATA + "=\"" + k.getAbsolutePath() + "\"", null);
    Uri newUri = getContentResolver().insert(uri, values);
    RingtoneManager.setActualDefaultRingtoneUri(
            this,
            RingtoneManager.TYPE_RINGTONE,
            newUri);
    Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    switch (requestCode) {
      case WRITE_SETTINGS_REQUEST_CODE:
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
          settingNewRingtone();
          break;
        }
    }
  }

  private boolean copySongToRingtoneDir() {
//    String fileName = playerManager.getCurrentSong().getName() + "_" + playerManager.getCurrentSong().getArtist() + ".mp3";
//    String sourcePath = getFilesDir().toString() + "/" + fileName;
//    File source = new File(sourcePath);
//    String destinationPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).toString();
//    try {
//      FileUtils.copyFile(source, destination);
//      return true;
//    } catch (IOException e) {
//      e.printStackTrace();
    return false;
//    }
  }

  @Override
  public void onBackPressed() {
    FragmentManager fragmentManager = getSupportFragmentManager();
    int entryCount = fragmentManager.getBackStackEntryCount();
    if (entryCount == 0) {
      finish();
    } else if (entryCount == 1) { // back to main screen
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
        downloadSongIv.setVisibility(View.GONE);
        shareSongIv.setVisibility(View.GONE);
        ringtoneIv.setVisibility(View.VISIBLE);
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
          Toast.makeText(HomeActivity.this, getString(R.string.song_not_found_message), Toast.LENGTH_SHORT).show();
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

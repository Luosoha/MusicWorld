package techkids.vn.music.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techkids.vn.music.R;
import techkids.vn.music.callbacks.OnBackFromMainPlayerListener;
import techkids.vn.music.callbacks.OnMusicPlayerActionListener;
import techkids.vn.music.callbacks.OnSongReadyListener;
import techkids.vn.music.fragments.ViewPagerFragment;
import techkids.vn.music.managers.PlayerManager;
import techkids.vn.music.managers.RealmContext;
import techkids.vn.music.managers.RetrofitContext;
import techkids.vn.music.networks.models.SearchSongResponseBody;
import techkids.vn.music.networks.models.Song;
import techkids.vn.music.networks.models.SongCategoryResponse;
import techkids.vn.music.networks.models.Subgenres;
import techkids.vn.music.screens.mainplayer.MainPlayerFragment;
import techkids.vn.music.screens.mainplayer.MainPlayerPresenter;
import techkids.vn.music.utils.ActionHelper;

public class MainActivity extends BaseActivity
        implements FragmentManager.OnBackStackChangedListener, OnBackFromMainPlayerListener, OnMusicPlayerActionListener {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.rl_mini_player)
  RelativeLayout miniPlayerLayout;
  @BindView(R.id.iv_back_from_main_player)
  ImageView backFromMainPlayerIv;
  @BindView(R.id.iv_download_song)
  ImageView downloadSongIv;
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

  private PlayerManager playerManager;
  private Song currentSong;
  private Handler handler = new Handler();
  private Runnable runnable;
  private OnSongReadyListener onSongReadyListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setSupportActionBar(toolbar);
    PlayerManager.init(this, this);
    playerManager = PlayerManager.getInstance();
  }

  @Override
  protected void onStart() {
    super.onStart();
    getSongCategories();
    addListener();
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_main;
  }

  private void getSongCategoriesFromRealm() {
    Subgenres.subgenres.addAll(RealmContext.getInstance().allSubgenres());
    hideProgress();
  }

  private void getSongCategories() {
    showProgress();
    if (RealmContext.getInstance().allSubgenres().isEmpty()) {
      RetrofitContext.getAlbumTypes().enqueue(new Callback<SongCategoryResponse>() {
        @Override
        public void onResponse(Call<SongCategoryResponse> call, Response<SongCategoryResponse> response) {
          ArrayList<Subgenres> songCategories = new ArrayList<>();
          songCategories.addAll(response.body().getBody().getMap().values());
          RealmContext.getInstance().deleteAll();
          for (Subgenres s : songCategories) {
            RealmContext.getInstance().insertSubgenre(s);
            Subgenres.subgenres.add(s);
          }
          changeFragment(R.id.fl_container, new ViewPagerFragment(), false);
          hideProgress();
        }

        @Override
        public void onFailure(Call<SongCategoryResponse> call, Throwable t) {
          hideProgress();
        }
      });
    } else {
      getSongCategoriesFromRealm();
      changeFragment(R.id.fl_container, new ViewPagerFragment(), false);
    }
  }

  private void addListener() {
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
        int currentPosition = (int) (progressSb.getProgress() * playerManager.getSongDuration() / progressSb.getMax());
        playerManager.seekTo(currentPosition);
        handler.postDelayed(runnable, 100);
      }
    });
  }

  @OnClick(R.id.fab_action)
  public void onFabActionClick() {
    if (playerManager.isPlaying()) {
      actionFab.setImageResource(R.drawable.ic_play_arrow_white_24px);
      playerManager.setIsPlaying(!playerManager.isPlaying());
      if (playerManager.isPlaying()) {
        playerManager.setIsPlaying(false);
      }
    } else {
      actionFab.setImageResource(R.drawable.ic_pause_white_24px);
      playerManager.setIsPlaying(!playerManager.isPlaying());
      if (!playerManager.isNull()) {
        playerManager.setIsPlaying(true);
      }
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

    changeFragment(R.id.fl_container, mainPlayerFragment, true);
  }

  @OnClick(R.id.iv_back_from_main_player)
  public void onBackFromMainPlayerEvent() {
    onBackPressed();
  }

  @Override
  public void onBackPressed() {
    if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
      finish();
    } else {
      getSupportActionBar().show();
      getSupportFragmentManager().popBackStack();
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
        }
        progressSb.setMax(playerManager.getSongDuration());
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
      playerManager.setIsPlaying(!playerManager.isPlaying());

      songNameTv.setText(song.getName());
      songArtistTv.setText(song.getArtist());
      actionFab.setImageResource(R.drawable.ic_pause_white_24px);
      Picasso.with(this).load(song.getIconUrl()).into(songImageCiv);
      if (doRevealMiniPlayer) {
        miniPlayerLayout.setVisibility(View.VISIBLE);
      }

      // Setup exo player
      playerManager.playNewSong(songUrl);

      if (!doRevealMiniPlayer) {
        sentDurationToMainPlayer = false;
        songNameInsideToolBarTv.setText(playerManager.getCurrentSong().getName());
        songArtistInsideToolBarTv.setText(playerManager.getCurrentSong().getArtist());
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
    if (playerManager.isPlaying()) {
      playerManager.setIsPlaying(false);
    }
  }

  @Override
  public void onResumeAction() {
    playerManager.setIsPlaying(true);
    actionFab.setImageResource(R.drawable.ic_pause_white_24px);
    if (!playerManager.isNull()) {
      playerManager.setIsPlaying(true);
    }
  }

  @Override
  public void onProgressChanged(int progress) {
    playerManager.seekTo(progress);
  }

  @Override
  public void onSongEnded() {
    int position = ActionHelper.findNextSongPositionOf(playerManager.getCurrentSong());
    searchForNextSongUrl(position);
  }

  private void searchForNextSongUrl(int position) {
    showProgress();
    final Song song = Song.SONGS.get(position);
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

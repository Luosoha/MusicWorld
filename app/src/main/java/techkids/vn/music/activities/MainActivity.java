package techkids.vn.music.activities;

import android.net.Uri;
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

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.Util;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techkids.vn.music.R;
import techkids.vn.music.callbacks.OnBackFromMainPlayerListener;
import techkids.vn.music.callbacks.OnPauseListener;
import techkids.vn.music.callbacks.OnSongReadyListener;
import techkids.vn.music.events.MusicProgressChangedEvent;
import techkids.vn.music.events.OpenMainPlayerEvent;
import techkids.vn.music.events.PlaySongEvent;
import techkids.vn.music.events.ResumeTheMusicFromMainPlayerEvent;
import techkids.vn.music.fragments.MainPlayerFragment;
import techkids.vn.music.fragments.ViewPagerFragment;
import techkids.vn.music.managers.RealmContext;
import techkids.vn.music.managers.RetrofitContext;
import techkids.vn.music.networks.models.Song;
import techkids.vn.music.networks.models.SongCategoryResponse;
import techkids.vn.music.networks.models.Subgenres;

public class MainActivity extends BaseActivity
        implements FragmentManager.OnBackStackChangedListener, OnBackFromMainPlayerListener, OnPauseListener {

  private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
  private static final int BUFFER_SEGMENT_COUNT = 256;

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

  private boolean songIsPlaying = false;
  public static ExoPlayer exoPlayer;
  private MediaCodecAudioTrackRenderer audioRenderer;
  private Song currentSong;
  private Handler handler = new Handler();
  private Runnable runnable;
  private OnSongReadyListener onSongReadyListener;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setSupportActionBar(toolbar);
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
        int currentPosition = (int) (progressSb.getProgress() * exoPlayer.getDuration() / progressSb.getMax());
        exoPlayer.seekTo(currentPosition);
        handler.postDelayed(runnable, 100);
      }
    });
  }

  @OnClick(R.id.fab_action)
  public void onFabActionClick() {
    if (songIsPlaying) {
      actionFab.setImageResource(R.drawable.ic_play_arrow_white_24px);
      songIsPlaying = !songIsPlaying;
      if (exoPlayer != null && exoPlayer.getPlayWhenReady()) {
        exoPlayer.setPlayWhenReady(false);
      }
    } else {
      actionFab.setImageResource(R.drawable.ic_pause_white_24px);
      songIsPlaying = !songIsPlaying;
      if (exoPlayer != null) {
        exoPlayer.setPlayWhenReady(true);
      }
    }
  }

  @OnClick(R.id.rl_mini_player)
  public void onMiniPlayerClick() {
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    miniPlayerLayout.setVisibility(View.GONE);

    mainPlayerToolbar.setVisibility(View.VISIBLE);
    songNameInsideToolBarTv.setText(currentSong.getName());
    songArtistInsideToolBarTv.setText(currentSong.getArtist());

    MainPlayerFragment mainPlayerFragment = new MainPlayerFragment();
    mainPlayerFragment.setOnBackFromMainPlayerListener(this);
    mainPlayerFragment.setOnPauseListener(this);
    onSongReadyListener = mainPlayerFragment;
    changeFragment(R.id.fl_container, mainPlayerFragment, true);
    EventBus.getDefault().postSticky(
            new OpenMainPlayerEvent(currentSong, songIsPlaying)
    );
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

  @Subscribe
  public void onEvent(PlaySongEvent playSongEvent) {
    if (playSongEvent.getSong() != null) {
      if (exoPlayer != null && exoPlayer.getPlayWhenReady()) {
        exoPlayer.stop();
      }

      currentSong = playSongEvent.getSong();
      songIsPlaying = true;
      actionFab.setImageResource(R.drawable.ic_pause_white_24px);

      Picasso.with(this).load(playSongEvent.getSong().getIconUrl()).into(songImageCiv);
      songNameTv.setText(playSongEvent.getSong().getName());
      songArtistTv.setText(playSongEvent.getSong().getArtist());
      if (playSongEvent.isRevealMiniPlayer()) {
        miniPlayerLayout.setVisibility(View.VISIBLE);
      }

      // Setup exo player
      exoPlayer = ExoPlayer.Factory.newInstance(1);
      String url = playSongEvent.getSongSource();
      Uri radioUri = Uri.parse(url);
      Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
      String userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
      DataSource dataSource = new DefaultUriDataSource(this, null, userAgent);
      ExtractorSampleSource sampleSource = new ExtractorSampleSource(
              radioUri, dataSource, allocator, BUFFER_SEGMENT_SIZE * BUFFER_SEGMENT_COUNT);
      audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
      exoPlayer.prepare(audioRenderer);
      exoPlayer.setPlayWhenReady(true);

      if (!playSongEvent.isRevealMiniPlayer()) {
        sentDurationToMainPlayer = false;
        songNameInsideToolBarTv.setText(currentSong.getName());
        songArtistInsideToolBarTv.setText(currentSong.getArtist());
      }

      startSeekbarProgress();
      handler.postDelayed(runnable, 100);
    } else {
      Toast.makeText(this, getString(R.string.song_not_found_message), Toast.LENGTH_SHORT).show();
    }
  }

  private boolean sentDurationToMainPlayer = false;

  private void startSeekbarProgress() {
    runnable = new Runnable() {
      @Override
      public void run() {
        if (exoPlayer.getDuration() > 0 && exoPlayer.getCurrentPosition() > 0 && !sentDurationToMainPlayer) {
          if (onSongReadyListener != null) {
            onSongReadyListener.onSongReady();
          }
          sentDurationToMainPlayer = true;
        }
        progressSb.setMax((int) exoPlayer.getDuration());
        handler.postDelayed(this, 100);
        progressSb.setProgress((int) exoPlayer.getCurrentPosition());
      }
    };
  }

  @Subscribe
  public void onEvent(ResumeTheMusicFromMainPlayerEvent event) {
    songIsPlaying = true;
    actionFab.setImageResource(R.drawable.ic_pause_white_24px);
    if (exoPlayer != null) {
      exoPlayer.setPlayWhenReady(true);
    }
  }

  @Subscribe
  public void onEvent(MusicProgressChangedEvent event) {
    exoPlayer.seekTo(event.getPosition());
  }

  @Override
  public void onBackFromMainPlayer() {
    miniPlayerLayout.setVisibility(View.VISIBLE);
    mainPlayerToolbar.setVisibility(View.GONE);
    getSupportActionBar().setDisplayShowTitleEnabled(true);
  }

  @Override
  protected void onDestroy() {
    if (exoPlayer != null) {
      exoPlayer.release();
    }
    super.onDestroy();
  }

  @Override
  public void onBackStackChanged() {

  }

  @Override
  public void onPauseAction() {
    songIsPlaying = false;
    actionFab.setImageResource(R.drawable.ic_play_arrow_white_24px);
    if (exoPlayer != null && exoPlayer.getPlayWhenReady()) {
      exoPlayer.setPlayWhenReady(false);
    }
  }
}

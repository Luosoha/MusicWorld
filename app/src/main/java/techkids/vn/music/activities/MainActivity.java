package techkids.vn.music.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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
import techkids.vn.music.events.BackFromMainPlayerEvent;
import techkids.vn.music.events.MusicProgressChangedEvent;
import techkids.vn.music.events.OpenMainPlayerEvent;
import techkids.vn.music.events.OpenTopsongsFragmentEvent;
import techkids.vn.music.events.PauseTheMusicFromMainPlayerEvent;
import techkids.vn.music.events.PlaySongEvent;
import techkids.vn.music.events.ResumeTheMusicFromMainPlayerEvent;
import techkids.vn.music.events.SongIsReadyEvent;
import techkids.vn.music.fragments.MainPlayerFragment;
import techkids.vn.music.fragments.TopsongsFragment;
import techkids.vn.music.fragments.ViewPagerFragment;
import techkids.vn.music.managers.RealmContext;
import techkids.vn.music.managers.RetrofitContext;
import techkids.vn.music.networks.models.Song;
import techkids.vn.music.networks.models.SongCategoryResponse;
import techkids.vn.music.networks.models.Subgenres;

public class MainActivity extends BaseActivity implements FragmentManager.OnBackStackChangedListener {

  private static final String TAG = MainActivity.class.toString();
  private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
  private static final int BUFFER_SEGMENT_COUNT = 256;

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.rl_mini_player)
  RelativeLayout rlMiniPlayer;
  @BindView(R.id.iv_back_from_main_player)
  ImageView ivBackFromMainPlayer;
  @BindView(R.id.iv_download_song)
  ImageView ivDownloadSong;
  @BindView(R.id.ll_main_player_toolbar)
  LinearLayout llMainPlayerToolbar;
  @BindView(R.id.sb_progress)
  SeekBar sbProgress;
  @BindView(R.id.tv_song_name_inside_tool_bar)
  TextView tvSongNameInsideToolBar;
  @BindView(R.id.tv_song_artist_inside_tool_bar)
  TextView tvSongArtistInsideToolBar;
  @BindView(R.id.civ_top_song)
  CircleImageView civSongImage;
  @BindView(R.id.tv_top_song_name)
  TextView tvSongName;
  @BindView(R.id.tv_top_song_artist)
  TextView tvSongArtist;
  @BindView(R.id.fab_action)
  FloatingActionButton fabAction;

  private boolean songIsPlaying = false;
  private ExoPlayer exoPlayer;
  private MediaCodecAudioTrackRenderer audioRenderer;
  private Song currentSong;
  private Handler mHandler = new Handler();
  private Runnable mRunnable;

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
          changeFragment(new ViewPagerFragment(), false);
          hideProgress();
        }

        @Override
        public void onFailure(Call<SongCategoryResponse> call, Throwable t) {
          hideProgress();
        }
      });
    } else {
      getSongCategoriesFromRealm();
      changeFragment(new ViewPagerFragment(), false);
    }
  }

  private void changeFragment(Fragment fragment, boolean addToBackStack) {
    if (addToBackStack) {
      getSupportFragmentManager().beginTransaction()
              .replace(R.id.fl_container, fragment)
              .addToBackStack(null)
              .commit();
    } else {
      getSupportFragmentManager().beginTransaction()
              .replace(R.id.fl_container, fragment)
              .commit();
    }
  }

  @OnClick(R.id.fab_action)
  public void onFabActionClick() {
    if (songIsPlaying) {
      fabAction.setImageResource(R.drawable.ic_play_arrow_white_24px);
      songIsPlaying = !songIsPlaying;
      if (exoPlayer != null && exoPlayer.getPlayWhenReady()) {
        exoPlayer.setPlayWhenReady(false);
      }
    } else {
      fabAction.setImageResource(R.drawable.ic_pause_white_24px);
      songIsPlaying = !songIsPlaying;
      if (exoPlayer != null) {
        exoPlayer.setPlayWhenReady(true);
      }
    }
  }

  @OnClick(R.id.rl_mini_player)
  public void onMiniPlayerClick() {
    getSupportActionBar().setDisplayShowTitleEnabled(false);
    rlMiniPlayer.setVisibility(View.GONE);

    llMainPlayerToolbar.setVisibility(View.VISIBLE);
    tvSongNameInsideToolBar.setText(currentSong.getName());
    tvSongArtistInsideToolBar.setText(currentSong.getArtist());

    changeFragment(new MainPlayerFragment(), true);
    EventBus.getDefault().postSticky(
            new OpenMainPlayerEvent(currentSong, sbProgress.getProgress(), sbProgress.getMax(), songIsPlaying)
    );
  }

  @OnClick(R.id.iv_back_from_main_player)
  public void onBackFromMainPlayerEvent() {
    onBackPressed();
  }

  private void addListener() {
    sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        mHandler.removeCallbacks(mRunnable);
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        int currentPosition = (int) (sbProgress.getProgress() * exoPlayer.getDuration() / sbProgress.getMax());
        exoPlayer.seekTo(currentPosition);
        mHandler.postDelayed(mRunnable, 100);
      }
    });
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
  public void onEvent(OpenTopsongsFragmentEvent openTopsongsFragmentEvent) {
    TopsongsFragment topsongsFragment = (TopsongsFragment) openTopsongsFragmentEvent.getFragment();
    topsongsFragment.setSub(openTopsongsFragmentEvent.getSubgenres());
    changeFragment(topsongsFragment, openTopsongsFragmentEvent.isAddToBackStack());
  }

  @Subscribe
  public void onEvent(PlaySongEvent playSongEvent) {
    if (exoPlayer != null && exoPlayer.getPlayWhenReady()) {
      exoPlayer.stop();
    }

    currentSong = playSongEvent.getSong();
    songIsPlaying = true;
    fabAction.setImageResource(R.drawable.ic_pause_white_24px);

    Picasso.with(this).load(playSongEvent.getSong().getIconUrl()).into(civSongImage);
    tvSongName.setText(playSongEvent.getSong().getName());
    tvSongArtist.setText(playSongEvent.getSong().getArtist());
    if (playSongEvent.isRevealMiniPlayer()) {
      rlMiniPlayer.setVisibility(View.VISIBLE);
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
      sendedDurationToMainPlayer = false;
      tvSongNameInsideToolBar.setText(currentSong.getName());
      tvSongArtistInsideToolBar.setText(currentSong.getArtist());
    }

    startSeekbarProgress();
    mHandler.postDelayed(mRunnable, 100);
  }

  private boolean sendedDurationToMainPlayer = false;

  private void startSeekbarProgress() {
    mRunnable = new Runnable() {
      @Override
      public void run() {
        if (exoPlayer.getDuration() > 0 && exoPlayer.getCurrentPosition() > 0 && !sendedDurationToMainPlayer) {
          EventBus.getDefault().postSticky(new SongIsReadyEvent((int) exoPlayer.getDuration()));
          sendedDurationToMainPlayer = true;
        }
        sbProgress.setMax((int) exoPlayer.getDuration());
        mHandler.postDelayed(this, 100);
        sbProgress.setProgress((int) exoPlayer.getCurrentPosition());
      }
    };
  }

  @Subscribe
  public void onEvent(BackFromMainPlayerEvent backFromMainPlayerEvent) {
    rlMiniPlayer.setVisibility(View.VISIBLE);
    llMainPlayerToolbar.setVisibility(View.GONE);
    getSupportActionBar().setDisplayShowTitleEnabled(true);
  }

  @Subscribe
  public void onEvent(PauseTheMusicFromMainPlayerEvent event) {
    songIsPlaying = false;
    fabAction.setImageResource(R.drawable.ic_play_arrow_white_24px);
    if (exoPlayer != null && exoPlayer.getPlayWhenReady()) {
      exoPlayer.setPlayWhenReady(false);
    }
  }

  @Subscribe
  public void onEvent(ResumeTheMusicFromMainPlayerEvent event) {
    songIsPlaying = true;
    fabAction.setImageResource(R.drawable.ic_pause_white_24px);
    if (exoPlayer != null) {
      exoPlayer.setPlayWhenReady(true);
    }
  }

  @Subscribe
  public void onEvent(MusicProgressChangedEvent event) {
    exoPlayer.seekTo(event.getPosition());
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

}

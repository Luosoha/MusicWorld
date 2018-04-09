package techkids.vn.music.fragments;


import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techkids.vn.music.R;
import techkids.vn.music.activities.MainActivity;
import techkids.vn.music.callbacks.OnBackFromMainPlayerListener;
import techkids.vn.music.callbacks.OnMusicPlayerActionListener;
import techkids.vn.music.callbacks.OnSongReadyListener;
import techkids.vn.music.events.OpenMainPlayerEvent;
import techkids.vn.music.events.PlaySongEvent;
import techkids.vn.music.managers.RetrofitContext;
import techkids.vn.music.networks.models.SearchSongResponseBody;
import techkids.vn.music.networks.models.Song;
import techkids.vn.music.utils.ActionHelper;

import static techkids.vn.music.activities.MainActivity.exoPlayer;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainPlayerFragment extends BaseFragment implements OnSongReadyListener {

  private static final String TAG = MainPlayerFragment.class.toString();

  @BindView(R.id.iv_song_image)
  ImageView songImageIv;
  @BindView(R.id.sb_progress)
  SeekBar progressSb;
  @BindView(R.id.sb_transparent_progress)
  SeekBar transparentProgressSb;
  @BindView(R.id.main_player_action_btn)
  FloatingActionButton actionFab;

  private Runnable runnable;
  private Handler handler;
  private Song currentSong;
  private boolean songIsPlaying;
  private OnMusicPlayerActionListener onMusicPlayerActionListener;
  private OnBackFromMainPlayerListener onBackFromMainPlayerListener;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_main_player;
  }

  @Override
  protected void initLayout() {
    init();
    addListener();
    startSeekBarsProgress();
    handler.postDelayed(runnable, 100);
  }

  private void init() {
    ((MainActivity) getActivity()).getSupportActionBar().show();
    handler = new Handler();
  }

  private void startSeekBarsProgress() {
    runnable = new Runnable() {
      @Override
      public void run() {
        if (songIsPlaying) {
          setProgressForSeekBars((int) exoPlayer.getCurrentPosition());
        }
        handler.postDelayed(this, 100);
      }
    };
  }

  private void addListener() {
    transparentProgressSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        progressSb.setProgress(transparentProgressSb.getProgress());
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(runnable);
        progressSb.setProgress(transparentProgressSb.getProgress());
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        progressSb.setProgress(transparentProgressSb.getProgress());
        handler.postDelayed(runnable, 100);
        onMusicPlayerActionListener.onProgressChanged(transparentProgressSb.getProgress());
      }
    });

    progressSb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
      @Override
      public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        transparentProgressSb.setProgress(progressSb.getProgress());
      }

      @Override
      public void onStartTrackingTouch(SeekBar seekBar) {
        handler.removeCallbacks(runnable);
        transparentProgressSb.setProgress(progressSb.getProgress());
      }

      @Override
      public void onStopTrackingTouch(SeekBar seekBar) {
        transparentProgressSb.setProgress(progressSb.getProgress());
        handler.postDelayed(runnable, 100);
        onMusicPlayerActionListener.onProgressChanged(progressSb.getProgress());
      }
    });
  }

  @OnClick(R.id.main_player_action_btn)
  public void onFabActionClick() {
    if (songIsPlaying) {
      actionFab.setImageResource(R.drawable.ic_play_arrow_white_24px);
      songIsPlaying = !songIsPlaying;
      handler.removeCallbacks(runnable);
      if (onMusicPlayerActionListener != null) {
        onMusicPlayerActionListener.onPauseAction();
      }

    } else {
      actionFab.setImageResource(R.drawable.ic_pause_white_24px);
      songIsPlaying = !songIsPlaying;
      handler.postDelayed(runnable, 100);
      onMusicPlayerActionListener.onResumeAction();
    }
  }

  @OnClick(R.id.iv_skip_previous)
  public void onPreviousSongClick() {
    Song song = Song.SONGS.get(ActionHelper.findPreviousSongPositionOf(currentSong));
    currentSong = song;
    songIsPlaying = false;
    progressSb.setProgress(0);
    transparentProgressSb.setProgress(0);
    Picasso.with(getActivity()).load(song.getImageUrl()).into(songImageIv);
    getSongSourceForMainActivity(song);
  }

  @OnClick(R.id.iv_skip_next)
  public void onNextSongClick() {
    Song song = Song.SONGS.get(ActionHelper.findNextSongPositionOf(currentSong));
    currentSong = song;
    songIsPlaying = false;
    progressSb.setProgress(0);
    transparentProgressSb.setProgress(0);
    Picasso.with(getActivity()).load(song.getImageUrl()).into(songImageIv);
    getSongSourceForMainActivity(song);
  }

  private void getSongSourceForMainActivity(final Song song) {
    String keyword = song.getName() + " " + song.getArtist();
    RetrofitContext.getSearchSong(keyword).enqueue(new Callback<SearchSongResponseBody>() {
      @Override
      public void onResponse(Call<SearchSongResponseBody> call, Response<SearchSongResponseBody> response) {
        SearchSongResponseBody songs = response.body();
        if (songs != null && !songs.getSongs().isEmpty()) {
          EventBus.getDefault().post(new PlaySongEvent(song, songs.getSongUrl(), false));
        }
      }

      @Override
      public void onFailure(Call<SearchSongResponseBody> call, Throwable t) {
        Log.d(TAG, "onFailure: " + t.toString());
      }
    });
  }

  @Subscribe(sticky = true)
  public void onEvent(OpenMainPlayerEvent openMainPlayerEvent) {
    currentSong = openMainPlayerEvent.getCurrentSong();
    songIsPlaying = openMainPlayerEvent.isPlaying();

    if (songIsPlaying) {
      actionFab.setImageResource(R.drawable.ic_pause_white_24px);
    } else {
      actionFab.setImageResource(R.drawable.ic_play_arrow_white_24px);
    }

    setDurationForSeekBars();
    setProgressForSeekBars((int) exoPlayer.getCurrentPosition());
    Picasso.with(getActivity()).load(openMainPlayerEvent.getCurrentSong().getImageUrl()).into(songImageIv);
    EventBus.getDefault().removeStickyEvent(OpenMainPlayerEvent.class);
  }

  @Override
  public void onSongReady() {
    setDurationForSeekBars();
    songIsPlaying = true;
  }

  private void setDurationForSeekBars() {
    progressSb.setMax((int) exoPlayer.getDuration());
    transparentProgressSb.setMax((int) exoPlayer.getDuration());
  }

  private void setProgressForSeekBars(int progress) {
    progressSb.setProgress(progress);
    transparentProgressSb.setProgress(progress);
  }

  public void setOnBackFromMainPlayerListener(OnBackFromMainPlayerListener listener) {
    onBackFromMainPlayerListener = listener;
  }

  public void setOnMusicPlayerActionListener(OnMusicPlayerActionListener listener) {
     onMusicPlayerActionListener = listener;
  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    if (onBackFromMainPlayerListener != null) {
      onBackFromMainPlayerListener.onBackFromMainPlayer();
    }
    EventBus.getDefault().unregister(this);
  }

}

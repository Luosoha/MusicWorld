package my.awesome.music.screens.mainplayer;

import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import my.awesome.music.R;
import my.awesome.music.activities.MainActivity;
import my.awesome.music.base.ViewFragment;
import my.awesome.music.callbacks.OnBackFromMainPlayerListener;
import my.awesome.music.callbacks.OnMusicPlayerActionListener;
import my.awesome.music.callbacks.OnSongReadyListener;
import my.awesome.music.managers.PlayerManager;
import my.awesome.music.networks.models.Song;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class MainPlayerFragment extends ViewFragment<MainPlayerContract.Presenter>
    implements MainPlayerContract.View, OnSongReadyListener {

  @BindView(R.id.iv_song_image)
  ImageView songImageIv;
  @BindView(R.id.sb_progress)
  SeekBar progressSb;
  @BindView(R.id.sb_transparent_progress)
  SeekBar transparentProgressSb;
  @BindView(R.id.main_player_action_btn)
  FloatingActionButton actionFab;

  private PlayerManager playerManager;
  private Runnable runnable;
  private Handler handler = new Handler();
  private OnMusicPlayerActionListener onMusicPlayerActionListener;
  private OnBackFromMainPlayerListener onBackFromMainPlayerListener;
  private SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
      progressSb.setProgress(transparentProgressSb.getProgress());
      transparentProgressSb.setProgress(transparentProgressSb.getProgress());
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
      handler.removeCallbacks(runnable);
      progressSb.setProgress(transparentProgressSb.getProgress());
      transparentProgressSb.setProgress(transparentProgressSb.getProgress());
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
      progressSb.setProgress(transparentProgressSb.getProgress());
      transparentProgressSb.setProgress(transparentProgressSb.getProgress());
      handler.postDelayed(runnable, 100);
      onMusicPlayerActionListener.onProgressChanged(progressSb.getProgress());
    }
  };

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_main_player;
  }

  @Override
  public void initLayout() {
    playerManager = PlayerManager.getInstance();
    setupMainPlayer();
    addListener();
  }

  private void setupMainPlayer() {
    MainActivity activity = (MainActivity) getActivity();
    activity.getSupportActionBar().show();
    onMusicPlayerActionListener = activity;

    if (playerManager.isPlaying()) {
      actionFab.setImageResource(R.drawable.ic_pause_white_24px);
    } else {
      actionFab.setImageResource(R.drawable.ic_play_arrow_white_24px);
    }
    Picasso.with(getActivity()).load(playerManager.getCurrentSong().getImageUrl()).into(songImageIv);

    setDurationForSeekBars();
    setProgressForSeekBars(playerManager.getCurrentPosition());
    startSeekBarsProgress();
  }

  private void startSeekBarsProgress() {
    runnable = new Runnable() {
      @Override
      public void run() {
        if (playerManager.isPlaying()) {
          setProgressForSeekBars(playerManager.getCurrentPosition());
        }
        handler.postDelayed(runnable, 100);
      }
    };
    handler.postDelayed(runnable, 100);
  }

  private void addListener() {
    transparentProgressSb.setOnSeekBarChangeListener(onSeekBarChangeListener);
    progressSb.setOnSeekBarChangeListener(onSeekBarChangeListener);
  }

  @OnClick(R.id.main_player_action_btn)
  public void onFabActionClick() {
    if (playerManager.isPlaying()) {
      actionFab.setImageResource(R.drawable.ic_play_arrow_white_24px);
      playerManager.setIsPlaying(!playerManager.isPlaying());
      handler.removeCallbacks(runnable);
      if (onMusicPlayerActionListener != null) {
        onMusicPlayerActionListener.onPauseAction();
      }

    } else {
      actionFab.setImageResource(R.drawable.ic_pause_white_24px);
      playerManager.setIsPlaying(!playerManager.isPlaying());
      handler.postDelayed(runnable, 100);
      if (onMusicPlayerActionListener != null) {
        onMusicPlayerActionListener.onResumeAction();
      }
    }
  }

  @OnClick(R.id.iv_skip_previous)
  public void onPreviousSongClick() {
    Song song = mPresenter.getPreviousSong(playerManager.getPlayList(), playerManager.getCurrentSong());
    setupForSong(song);
  }

  @OnClick(R.id.iv_skip_next)
  public void onNextSongClick() {
    Song song = mPresenter.getNextSong(playerManager.getPlayList(), playerManager.getCurrentSong());
    setupForSong(song);
  }

  private void setupForSong(Song song) {
    playerManager.setCurrentSong(song);
    playerManager.setIsPlaying(false);
    progressSb.setProgress(0);
    transparentProgressSb.setProgress(0);
    Picasso.with(getActivity()).load(song.getImageUrl()).into(songImageIv);
    actionFab.setImageResource(R.drawable.ic_pause_white_24px);
    mPresenter.searchForSong(song);
  }

  @Override
  public void onSongReady() {
    setDurationForSeekBars();
    playerManager.setIsPlaying(true);
    handler.postDelayed(runnable, 100);
  }

  @Override
  public void onNextSongReady(String songImageUrl) {
    showProgress();
    Picasso.with(getContext()).load(songImageUrl).into(songImageIv);
    hideProgress();
  }

  private void setDurationForSeekBars() {
    progressSb.setMax(playerManager.getSongDuration());
    transparentProgressSb.setMax(playerManager.getSongDuration());
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
  public void onStop() {
    super.onStop();
    if (onBackFromMainPlayerListener != null) {
      onBackFromMainPlayerListener.onBackFromMainPlayer();
    }
  }

  @Override
  public void onSongFound(Song song, String songUrl) {
    if (onMusicPlayerActionListener != null) {
      onMusicPlayerActionListener.onPlaySong(song, songUrl, false);
    }
  }

}

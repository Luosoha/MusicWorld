package hails.awesome.music.managers;

import android.content.Context;
import android.net.Uri;

import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.Util;

import java.util.ArrayList;

import hails.awesome.music.callbacks.OnMusicPlayerActionListener;
import hails.awesome.music.networks.models.Song;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 09/04/2018.
 */

public class PlayerManager {

  private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
  private static final int BUFFER_SEGMENT_COUNT = 256;

  private static PlayerManager instance;
  private ExoPlayer exoPlayer;
  private ExtractorSampleSource sampleSource;
  private DefaultAllocator allocator;
  private String userAgent;
  private DefaultUriDataSource dataSource;
  private MediaCodecAudioTrackRenderer audioRenderer;
  private OnMusicPlayerActionListener onMusicPlayerActionListener;
  private ArrayList<Song> playList = new ArrayList<>();
  private Song currentSong;
  private String songUrl;
  private Subgenres subgenres;
  private boolean isOfflineMode = false;

  private PlayerManager() {}

  private PlayerManager(Context context, OnMusicPlayerActionListener listener) {
    exoPlayer = ExoPlayer.Factory.newInstance(1);
    allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
    userAgent = Util.getUserAgent(context, "ExoPlayerDemo");
    dataSource = new DefaultUriDataSource(context, null, userAgent);
    onMusicPlayerActionListener = listener;
    addListener();
  }

  private void addListener() {
    exoPlayer.addListener(new ExoPlayer.Listener() {
      @Override
      public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        switch(playbackState) {
          case ExoPlayer.STATE_BUFFERING:
            break;
          case ExoPlayer.STATE_ENDED:
            if (onMusicPlayerActionListener != null) {
              onMusicPlayerActionListener.onSongEnded();
            }
            break;
          case ExoPlayer.STATE_IDLE:
            break;
          case ExoPlayer.STATE_PREPARING:
            break;
          case ExoPlayer.STATE_READY:
            break;
          default:
            break;
        }
      }

      @Override
      public void onPlayWhenReadyCommitted() {

      }

      @Override
      public void onPlayerError(ExoPlaybackException error) {

      }
    });
  }

  public static void init(Context context, OnMusicPlayerActionListener listener) {
    if (instance == null) {
      instance = new PlayerManager(context, listener);
    }
  }

  public static PlayerManager getInstance() {
    return instance;
  }

  public void playNewSong(String songUrl) {
    if (exoPlayer != null) {
      exoPlayer.seekTo(0);
    }
    Uri radioUri = Uri.parse(songUrl);
    sampleSource = new ExtractorSampleSource(
            radioUri, dataSource, allocator, BUFFER_SEGMENT_SIZE * BUFFER_SEGMENT_COUNT
    );
    audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
    exoPlayer.prepare(audioRenderer);
    setIsPlaying(true);
  }

  public boolean isNull() {
    return exoPlayer == null;
  }

  public int getSongDuration() {
    return (int) exoPlayer.getDuration();
  }

  public int getCurrentPosition() {
    return (int) exoPlayer.getCurrentPosition();
  }

  public void seekTo(int progress) {
    exoPlayer.seekTo(progress);
  }

  public boolean isPlaying() {
    return exoPlayer != null && exoPlayer.getPlayWhenReady();
  }

  public void setIsPlaying(boolean playWhenReady) {
    exoPlayer.setPlayWhenReady(playWhenReady);
  }

  public Song getCurrentSong() {
    return currentSong;
  }

  public void setCurrentSong(Song currentSong) {
    this.currentSong = currentSong;
  }

  public ArrayList<Song> getPlayList() {
    return playList;
  }

  public void setPlayList(ArrayList<Song> playList) {
    this.playList = playList;
  }

  public String getSongUrl() {
    return songUrl;
  }

  public void setSongUrl(String songUrl) {
    this.songUrl = songUrl;
  }

  public Subgenres getSubgenres() {
    return subgenres;
  }

  public void setSubgenres(Subgenres subgenres) {
    this.subgenres = subgenres;
  }

  public boolean isOfflineMode() {
    return isOfflineMode;
  }

  public void setOfflineMode(boolean offlineMode) {
    isOfflineMode = offlineMode;
  }

  public void release() {
    exoPlayer.release();
  }

  public void stop() {
    exoPlayer.stop();
  }

}

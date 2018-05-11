package hails.awesome.music.callbacks;

import hails.awesome.music.networks.models.Song;

/**
 * Created by HaiLS on 09/04/2018.
 */

public interface OnMusicPlayerActionListener {
  void onPlaySong(Song song, String songUrl, boolean doRevealMiniPlayer);

  void onPauseAction();

  void onResumeAction();

  void onProgressChanged(int progress);

  void onSongEnded();
}

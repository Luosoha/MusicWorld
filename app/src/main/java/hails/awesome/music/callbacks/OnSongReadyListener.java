package hails.awesome.music.callbacks;

/**
 * Created by HaiLS on 09/04/2018.
 */

public interface OnSongReadyListener {
  void onSongReady();

  void onNextSongReady(String songImageUrl);
}

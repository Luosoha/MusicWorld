package techkids.vn.music.callbacks;

/**
 * Created by HaiLS on 09/04/2018.
 */

public interface OnMusicPlayerActionListener {
  void onPauseAction();
  void onResumeAction();
  void onProgressChanged(int progress);
}

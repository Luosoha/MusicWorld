package hails.awesome.music.callbacks;

import hails.awesome.music.networks.models.Song;

/**
 * Created by HaiLS on 09/04/2018.
 */

public interface OnTopSongClickListener {
  void onSongClick(Song song);
}

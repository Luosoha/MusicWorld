package my.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class TopSongsResponseBody {

  @SerializedName("feed")
  private SongList songList;

  public TopSongsResponseBody(SongList songList) {
    this.songList = songList;
  }

  public SongList getSongList() {
    return songList;
  }

}

package my.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class SongList {

  @SerializedName("entry")
  private ArrayList<Song> list;

  public SongList(ArrayList<Song> list) {
    this.list = list;
  }

  public ArrayList<Song> getList() {
    return list;
  }

}

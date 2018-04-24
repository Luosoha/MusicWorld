package my.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class SongCategoryResponseBody {

  @SerializedName("id")
  private String id;
  @SerializedName("subgenres")
  private Map<String, Subgenres> map;

  public SongCategoryResponseBody() {
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Map<String, Subgenres> getMap() {
    return map;
  }

  public void setMap(Map<String, Subgenres> map) {
    this.map = map;
  }

}

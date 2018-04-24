package my.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lush on 1/8/2017.
 */

public class SongCategoryResponse {

  @SerializedName("34")
  private SongCategoryResponseBody body;

  public SongCategoryResponse() {
  }

  public SongCategoryResponseBody getBody() {
    return body;
  }

  public void setBody(SongCategoryResponseBody body) {
    this.body = body;
  }
}

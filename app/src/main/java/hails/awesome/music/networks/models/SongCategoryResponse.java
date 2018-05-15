package hails.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

/**
 * Category Response
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

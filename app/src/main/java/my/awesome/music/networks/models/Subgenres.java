package my.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class Subgenres extends RealmObject {

  @SerializedName("id")
  private String id;
  @SerializedName("name")
  private String name;

  private boolean isFavorite = false;

  public Subgenres() {
  }

  public Subgenres(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public boolean isFavorite() {
    return isFavorite;
  }

  public void setFavorite(boolean favorite) {
    isFavorite = favorite;
  }

  @Override
  public String toString() {
    return name;
  }

  public static ArrayList<Subgenres> subgenres = new ArrayList<>();

}

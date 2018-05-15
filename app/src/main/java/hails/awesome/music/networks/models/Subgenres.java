package hails.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by Lush on 1/8/2017.
 */

public class Subgenres extends RealmObject {

  @SerializedName("id")
  private String id;
  @SerializedName("name")
  private String name;
  private boolean isFavorite = false;

  public void setId(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setName(String name) {
    this.name = name;
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

  public static ArrayList<Subgenres> subgenres = new ArrayList<>();

  public static String TABLE_NAME = "Subgenres";
  public static String COLUMN_ID = "id";
  public static String COLUMN_NAME = "name";
  public static String COLUMN_IS_FAVORITE = "is_favorite";

  public static String SQL_CREATE_SUBGENRES_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
          COLUMN_ID + " TEXT PRIMARY KEY," +
          COLUMN_NAME + " TEXT," +
          COLUMN_IS_FAVORITE + " INTEGER)";


}

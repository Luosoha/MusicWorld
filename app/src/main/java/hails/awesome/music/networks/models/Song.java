package hails.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Object song
 */

public class Song {

  @SerializedName("im:name")
  private SongName name;
  @SerializedName("im:image")
  private SongImage[] images;
  @SerializedName("im:artist")
  private SongArtist artist;
  private Subgenres subgenres;

  public Song() {
  }

  public Song(SongName name, SongImage[] images, SongArtist artist) {
    this.name = name;
    this.images = images;
    this.artist = artist;
  }

  public void setName(String name) {
    this.name = new SongName();
    this.name.setName(name);
  }

  public String getName() {
    return name.getSongName();
  }

  public SongImage[] getImages() {
    return images;
  }

  public String getIconUrl() {
    return images[0].getUrl();
  }

  public String getImageUrl() {
    return images[2].getUrl();
  }

  public void setArtist(String artist) {
    this.artist = new SongArtist();
    this.artist.setName(artist);
  }

  public String getArtist() {
    return artist.getName();
  }

  public Subgenres getSubgenres() {
    return subgenres;
  }

  public void setSubgenres(Subgenres subgenres) {
    this.subgenres = subgenres;
  }

  class SongName {
    @SerializedName("label")
    private String name;

    public void setName(String name) {
      this.name = name;
    }

    public String getSongName() {
      return name;
    }
  }

  class SongImage {
    @SerializedName("label")
    private String url;

    public SongImage(String url) {
      this.url = url;
    }

    public String getUrl() {
      return url;
    }
  }

  class SongArtist {
    @SerializedName("label")
    private String name;

    public void setName(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }
  }

  public static String TABLE_NAME = "Song";
  public static String COLUMN_NAME = "name";
  public static String COLUMN_ARTIST = "artist";
  public static String COLUMN_ID_SUBGENRES = "id_subgenres";

  public static String SQL_CREATE_SONG_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
          COLUMN_NAME + " TEXT," +
          COLUMN_ARTIST + " TEXT," +
          COLUMN_ID_SUBGENRES + " TEXT, FOREIGN KEY (" + COLUMN_ID_SUBGENRES + ") REFERENCES " +
          Subgenres.TABLE_NAME + "(" + Subgenres.COLUMN_ID + "))";

}

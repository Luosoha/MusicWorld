package techkids.vn.music.networks.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Lush on 1/10/2017.
 */

public class Song {

  @SerializedName("im:name")
  private SongName name;
  @SerializedName("im:image")
  private SongImage[] images;
  @SerializedName("im:artist")
  private SongArtist artist;

  public Song(SongName name, SongImage[] images, SongArtist artist) {
    this.name = name;
    this.images = images;
    this.artist = artist;
  }

  public String getName() {
    return name.getSongName();
  }

  public String getIconUrl() {
    return images[0].getUrl();
  }

  public String getImageUrl() {
    return images[2].getUrl();
  }

  public String getArtist() {
    return artist.getName();
  }


  @Override
  public String toString() {
    return "Song{" +
            "name=" + name +
            ", images=" + Arrays.toString(images) +
            ", artist=" + artist +
            '}';
  }

  public static ArrayList<Song> SONGS = new ArrayList<>();

  class SongName {
    @SerializedName("label")
    private String name;

    public SongName(String name) {
      this.name = name;
    }

    public String getSongName() {
      return name;
    }

    @Override
    public String toString() {
      return "SongName{" +
              "name='" + name + '\'' +
              '}';
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

    @Override
    public String toString() {
      return "SongImage{" +
              "url='" + url + '\'' +
              '}';
    }
  }

  class SongArtist {
    @SerializedName("label")
    private String name;

    public SongArtist(String name) {
      this.name = name;
    }

    public String getName() {
      return name;
    }

    @Override
    public String toString() {
      return "SongArtist{" +
              "name='" + name + '\'' +
              '}';
    }
  }

}

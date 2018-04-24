package my.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lush on 1/15/2017.
 */

public class SearchSongResponseBody {

    @SerializedName("results")
    private ArrayList<SearchSong> songs;

    public SearchSongResponseBody(ArrayList<SearchSong> songs) {
        this.songs = songs;
    }

    public List<SearchSong> getSongs() {
        return songs;
    }

    public String getSongUrl() {
        return songs.get(0).getSongUrl();
    }

    class SearchSong {
        @SerializedName("previewUrl")
        private String songUrl;

        public String getSongUrl() {
            return songUrl;
        }

        public void setSongUrl(String songUrl) {
            this.songUrl = songUrl;
        }
    }

}

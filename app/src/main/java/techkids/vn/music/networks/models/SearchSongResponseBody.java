package techkids.vn.music.networks.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Lush on 1/15/2017.
 */

public class SearchSongResponseBody {

    @SerializedName("docs")
    private SearchSong[] songs;

    public SearchSongResponseBody(SearchSong[] songs) {
        this.songs = songs;
    }

    public SearchSong[] getSongs() {
        return songs;
    }

    @Override
    public String toString() {
        return "SearchSongResponseBody{" +
                "songs=" + songs[0].toString() +
                '}';
    }

}

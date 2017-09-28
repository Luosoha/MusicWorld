package techkids.vn.music.networks.json_models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by Lush on 1/10/2017.
 */

public class TopSongsResponseBody {

    @SerializedName("feed")
    private SongList songList;

    public TopSongsResponseBody(SongList songList) {
        this.songList = songList;
    }

    public SongList getSongList() {
        return songList;
    }

    @Override
    public String toString() {
        return "TopSongsResponseBody{" +
                "songList=" + songList +
                '}';
    }

}

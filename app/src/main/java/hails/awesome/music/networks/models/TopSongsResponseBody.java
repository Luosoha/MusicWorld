package hails.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

/**
 * Top songs
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

}

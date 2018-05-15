package hails.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Song list
 */

public class SongList {

    @SerializedName("entry")
    private Song[] list;

    public SongList(Song[] list) {
        this.list = list;
    }

    public Song[] getList() {
        return list;
    }

}

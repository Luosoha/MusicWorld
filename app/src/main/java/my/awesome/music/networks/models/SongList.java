package my.awesome.music.networks.models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by Lush on 1/10/2017.
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

    @Override
    public String toString() {
        return "SongList{" +
                "list=" + Arrays.toString(list) +
                '}';
    }

}

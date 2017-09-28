package techkids.vn.music.networks.json_models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by Lush on 1/8/2017.
 */

public class SongCategoryResponseBody {

    @SerializedName("id")
    private String id;

    @SerializedName("store")
    private String store;

    @SerializedName("subgenres")
    private Subgenres[] subgenres;

    public SongCategoryResponseBody() {
    }

    public SongCategoryResponseBody(String id, String store, Subgenres[] subgenres) {
        this.id = id;
        this.store = store;
        this.subgenres = subgenres;
    }

    public String getId() {
        return id;
    }

    public String getStore() {
        return store;
    }

    public Subgenres[] getSubgenres() {
        return subgenres;
    }

    @Override
    public String toString() {
        return "SongCategoryResponseBody{" +
                "id='" + id + '\'' +
                ", store='" + store + '\'' +
                ", subgenres=" + Arrays.toString(subgenres) +
                '}';
    }

}

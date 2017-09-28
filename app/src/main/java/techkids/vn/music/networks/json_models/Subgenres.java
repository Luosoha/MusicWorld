package techkids.vn.music.networks.json_models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import io.realm.RealmObject;

/**
 * Created by Lush on 1/8/2017.
 */

public class Subgenres extends RealmObject {

    @SerializedName("id")
    private String id;

    @SerializedName("translation_key")
    private String translationKey;

    private boolean isFavorite = false;

    public Subgenres() {
    }

    public Subgenres(String id, String translationKey) {
        this.id = id;
        this.translationKey = translationKey;
    }

    public String getId() {
        return id;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public String toString() {
        return translationKey;
    }

    public static ArrayList<Subgenres> subgenres = new ArrayList<>();

}

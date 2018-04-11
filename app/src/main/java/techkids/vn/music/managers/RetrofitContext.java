package techkids.vn.music.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import techkids.vn.music.networks.APIService;
import techkids.vn.music.networks.models.SearchSongResponseBody;
import techkids.vn.music.networks.models.SongCategoryResponse;
import techkids.vn.music.networks.models.TopSongsResponseBody;

/**
 * Created by Lush on 1/8/2017.
 */

public class RetrofitContext {

    private static Gson gson = new GsonBuilder().setLenient().create();

    public static final Retrofit ITUNES_CATEGORY = new Retrofit.Builder()
            .baseUrl("http://itunes.apple.com/WebObjects/MZStoreServices.woa/ws/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static final Retrofit ITUNES_TOP_SONG = new Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/us/rss/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final Retrofit ITUNES_SEARCH = new Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Call<SongCategoryResponse> getAlbumTypes() {
        return ITUNES_CATEGORY.create(APIService.class).getAlbumTypes();
    }

    public static Call<TopSongsResponseBody> getTopSongs(String id) {
        return ITUNES_TOP_SONG.create(APIService.class).getTopSongs(id);
    }

    public static Call<SearchSongResponseBody> getSearchSong(String keyword) {
        return ITUNES_SEARCH.create(APIService.class).getSearchSong(keyword, "music", "song");
    }

}

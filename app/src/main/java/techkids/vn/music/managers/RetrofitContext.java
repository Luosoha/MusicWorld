package techkids.vn.music.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import techkids.vn.music.networks.models.SearchSongResponseBody;
import techkids.vn.music.networks.models.SongCategoryResponse;
import techkids.vn.music.networks.models.TopSongsResponseBody;
import techkids.vn.music.networks.services.AlbumTypeService;
import techkids.vn.music.networks.services.SearchSongService;
import techkids.vn.music.networks.services.TopSongsService;

/**
 * Created by Lush on 1/8/2017.
 */

public class RetrofitContext {

    private static Gson gson = new GsonBuilder().setLenient().create();

    public static final Retrofit RSS_ITUNES = new Retrofit.Builder()
            .baseUrl("http://itunes.apple.com/WebObjects/MZStoreServices.woa/ws/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    public static final Retrofit ITUNES = new Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/us/rss/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final Retrofit MP3 = new Retrofit.Builder()
            .baseUrl("http://api.mp3.zing.vn/api/mobile/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Call<SongCategoryResponse> getAlbumTypes() {
        return RSS_ITUNES.create(AlbumTypeService.class).getAlbumTypes();
    }

    public static Call<TopSongsResponseBody> getTopSongs(String id) {
        return ITUNES.create(TopSongsService.class).getTopSongs(id);
    }

    public static Call<SearchSongResponseBody> getSearchSong(String data) {
        return MP3.create(SearchSongService.class).getSearchSong(data);
    }

}

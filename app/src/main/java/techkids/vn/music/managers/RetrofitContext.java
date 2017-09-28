package techkids.vn.music.managers;

import java.util.List;

import retrofit2.Call;
import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;
import techkids.vn.music.networks.json_models.SearchSongResponseBody;
import techkids.vn.music.networks.json_models.SongCategoryResponseBody;
import techkids.vn.music.networks.json_models.TopSongsResponseBody;
import techkids.vn.music.networks.services.AlbumTypeService;
import techkids.vn.music.networks.services.SearchSongService;
import techkids.vn.music.networks.services.TopSongsService;

/**
 * Created by Lush on 1/8/2017.
 */

public class RetrofitContext {

    public static final Retrofit RSS_ITUNES = new Retrofit.Builder()
            .baseUrl("https://rss.itunes.apple.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final Retrofit ITUNES = new Retrofit.Builder()
            .baseUrl("https://itunes.apple.com/us/rss/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static final Retrofit MP3 = new Retrofit.Builder()
            .baseUrl("http://api.mp3.zing.vn/api/mobile/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public static Call<List<SongCategoryResponseBody>> getAlbumTypes() {
        return RSS_ITUNES.create(AlbumTypeService.class).getAlbumTypes();
    }

    public static Call<TopSongsResponseBody> getTopSongs(String id) {
        return ITUNES.create(TopSongsService.class).getTopSongs(id);
    }

    public static Call<SearchSongResponseBody> getSearchSong(String data) {
        return MP3.create(SearchSongService.class).getSearchSong(data);
    }

}

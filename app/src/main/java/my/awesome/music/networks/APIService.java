package my.awesome.music.networks;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import my.awesome.music.networks.models.SearchSongResponseBody;
import my.awesome.music.networks.models.SongCategoryResponse;
import my.awesome.music.networks.models.TopSongsResponseBody;

/**
 * Created by HaiLS on 11/04/2018.
 */

public interface APIService {
  @GET("genres?id=34")
  Call<SongCategoryResponse> getAlbumTypes();

  @GET("topsongs/limit=50/genre={id}/explicit=true/json")
  Call<TopSongsResponseBody> getTopSongs(@Path("id") String id);

  @GET("search/song")
  Call<SearchSongResponseBody> getSearchSong(@Query("term") String keyword,
                                             @Query("media") String mediaType,
                                             @Query("entity") String entity);
}

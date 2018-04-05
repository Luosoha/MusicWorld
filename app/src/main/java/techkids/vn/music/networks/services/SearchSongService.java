package techkids.vn.music.networks.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import techkids.vn.music.networks.models.SearchSongResponseBody;

/**
 * Created by Lush on 1/15/2017.
 */

public interface SearchSongService {
    @GET("search/song")
    Call<SearchSongResponseBody> getSearchSong(@Query("term") String keyword,
                                               @Query("media") String mediaType,
                                               @Query("entity") String entity);
}

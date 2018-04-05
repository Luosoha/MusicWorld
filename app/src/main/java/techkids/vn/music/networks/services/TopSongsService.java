package techkids.vn.music.networks.services;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import techkids.vn.music.networks.models.TopSongsResponseBody;

/**
 * Created by Lush on 1/10/2017.
 */

public interface TopSongsService {
    @GET("topsongs/limit=50/genre={id}/explicit=true/json")
    Call<TopSongsResponseBody> getTopSongs(@Path("id") String id);
}

package techkids.vn.music.networks.services;

import retrofit2.Call;
import retrofit2.http.GET;
import techkids.vn.music.networks.models.SongCategoryResponse;

/**
 * Created by Lush on 1/8/2017.
 */

public interface AlbumTypeService {
    @GET("genres?id=34")
    Call<SongCategoryResponse> getAlbumTypes();
}

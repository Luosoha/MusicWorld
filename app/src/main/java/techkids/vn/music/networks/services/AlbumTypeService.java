package techkids.vn.music.networks.services;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import techkids.vn.music.networks.json_models.SongCategoryResponseBody;

/**
 * Created by Lush on 1/8/2017.
 */

public interface AlbumTypeService {
    @GET("/data/media-types.json")
    Call<List<SongCategoryResponseBody>> getAlbumTypes();
}

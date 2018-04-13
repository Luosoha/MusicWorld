package techkids.vn.music.screens.mainplayer;

import retrofit2.Callback;
import techkids.vn.music.base.Interactor;
import techkids.vn.music.managers.RetrofitContext;
import techkids.vn.music.networks.models.SearchSongResponseBody;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class MainPlayerInteractor extends Interactor<MainPlayerContract.Presenter>
    implements MainPlayerContract.Interactor {

  public MainPlayerInteractor(MainPlayerContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public void getSearchSong(String keyword, Callback<SearchSongResponseBody> callback) {
    RetrofitContext.getSearchSong(keyword).enqueue(callback);
  }
}

package hails.awesome.music.screens.mainplayer;

import retrofit2.Callback;
import hails.awesome.music.base.Interactor;
import hails.awesome.music.managers.RetrofitContext;
import hails.awesome.music.networks.models.SearchSongResponseBody;

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

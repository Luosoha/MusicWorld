package hails.awesome.music.screens.topsongs;

import retrofit2.Callback;
import hails.awesome.music.base.Interactor;
import hails.awesome.music.managers.RealmContext;
import hails.awesome.music.managers.RetrofitContext;
import hails.awesome.music.networks.models.Subgenres;
import hails.awesome.music.networks.models.TopSongsResponseBody;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class TopSongsInteractor extends Interactor<TopSongsContract.Presenter>
        implements TopSongsContract.Interactor {

  public TopSongsInteractor(TopSongsContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public void getTopSongs(String id, Callback<TopSongsResponseBody> callback) {
    RetrofitContext.getTopSongs(id).enqueue(callback);
  }

  @Override
  public void saveFavoriteSubgenres(int position) {
    RealmContext.getInstance().update(Subgenres.subgenres.get(position), !Subgenres.subgenres.get(position).isFavorite());
  }

}

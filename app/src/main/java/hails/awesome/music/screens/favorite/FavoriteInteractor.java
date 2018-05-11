package hails.awesome.music.screens.favorite;

import java.util.List;

import hails.awesome.music.base.Interactor;
import hails.awesome.music.managers.RealmContext;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class FavoriteInteractor extends Interactor<FavoriteContract.Presenter>
    implements FavoriteContract.Interactor {

  public FavoriteInteractor(FavoriteContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public List<Subgenres> getFavoriteGenres() {
    return RealmContext.getInstance().findAllFavoriteGenres();
  }

}

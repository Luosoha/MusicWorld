package techkids.vn.music.screens.favorite;

import java.util.List;

import techkids.vn.music.base.Interactor;
import techkids.vn.music.managers.RealmContext;
import techkids.vn.music.networks.models.Subgenres;

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

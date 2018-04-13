package techkids.vn.music.screens.favorite;

import java.util.ArrayList;
import java.util.List;

import techkids.vn.music.base.interfaces.IInteractor;
import techkids.vn.music.base.interfaces.IPresenter;
import techkids.vn.music.base.interfaces.IView;
import techkids.vn.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 13/04/2018.
 */

public interface FavoriteContract {
  interface Interactor extends IInteractor<Presenter> {
    List<Subgenres> getFavoriteGenres();
  }

  interface View extends IView<Presenter> {

  }

  interface Presenter extends IPresenter<View, Interactor> {
    ArrayList<String> getFavoriteGenres();
  }
}

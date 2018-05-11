package hails.awesome.music.screens.favorite;

import java.util.ArrayList;
import java.util.List;

import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;
import hails.awesome.music.networks.models.Subgenres;

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

    void goToTopSongScreen(String subgenresName);
  }
}

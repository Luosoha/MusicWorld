package my.awesome.music.screens.genres;

import my.awesome.music.base.interfaces.IInteractor;
import my.awesome.music.base.interfaces.IPresenter;
import my.awesome.music.base.interfaces.IView;
import my.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 11/04/2018.
 */

public interface GenresContract {
  interface Interactor extends IInteractor<Presenter> {
  }

  interface View extends IView<Presenter> {
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void goToTopSongsScreen(Subgenres subgenres);
  }
}

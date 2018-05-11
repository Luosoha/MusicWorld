package hails.awesome.music.screens.genres;

import android.support.v7.widget.RecyclerView;

import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;
import hails.awesome.music.networks.models.Subgenres;

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

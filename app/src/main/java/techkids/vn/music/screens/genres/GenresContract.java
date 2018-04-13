package techkids.vn.music.screens.genres;

import android.support.v7.widget.RecyclerView;

import techkids.vn.music.base.interfaces.IInteractor;
import techkids.vn.music.base.interfaces.IPresenter;
import techkids.vn.music.base.interfaces.IView;
import techkids.vn.music.networks.models.Subgenres;

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

package hails.awesome.music.screens.playlist;

import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;

/**
 * Created by HaiLS on 12/07/2018.
 */

public interface PlayListContract {
  interface Interactor extends IInteractor<Presenter> {

  }

  interface View extends IView<Presenter> {

  }

  interface Presenter extends IPresenter<View, Interactor> {

  }
}

package my.awesome.music.screens.viewpager;

import my.awesome.music.base.interfaces.IInteractor;
import my.awesome.music.base.interfaces.IPresenter;
import my.awesome.music.base.interfaces.IView;

/**
 * Created by HaiLS on 13/04/2018.
 */

public interface ViewPagerContract {
  interface Interactor extends IInteractor<Presenter> {

  }

  interface View extends IView<Presenter> {

  }

  interface Presenter extends IPresenter<View, Interactor> {

  }
}

package hails.awesome.music.screens.viewpager;

import hails.awesome.music.base.Presenter;
import hails.awesome.music.base.ViewFragment;
import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;

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

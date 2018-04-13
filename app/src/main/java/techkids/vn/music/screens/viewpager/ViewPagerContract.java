package techkids.vn.music.screens.viewpager;

import techkids.vn.music.base.Presenter;
import techkids.vn.music.base.ViewFragment;
import techkids.vn.music.base.interfaces.IInteractor;
import techkids.vn.music.base.interfaces.IPresenter;
import techkids.vn.music.base.interfaces.IView;

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

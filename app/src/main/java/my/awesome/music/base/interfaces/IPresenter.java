package my.awesome.music.base.interfaces;

import android.support.v4.app.Fragment;

/**
 * Created by HaiLS on 11/04/2018.
 */

public interface IPresenter<V extends IView, I extends IInteractor> {
  void start();

  I onCreateInteractor();

  I getInteractor();

  V onCreateView();

  Fragment getFragment();

  void back();
}


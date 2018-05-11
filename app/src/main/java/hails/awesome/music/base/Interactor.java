package hails.awesome.music.base;

import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;

/**
 * Created by HaiLS on 11/04/2018.
 */

public abstract class Interactor<P extends IPresenter> implements IInteractor<P> {
  protected P mPresenter;

  public Interactor(P presenter) {
    mPresenter = presenter;
  }

  public P getPresenter() {
    return mPresenter;
  }
}

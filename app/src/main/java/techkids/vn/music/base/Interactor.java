package techkids.vn.music.base;

import techkids.vn.music.base.interfaces.IInteractor;
import techkids.vn.music.base.interfaces.IPresenter;

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

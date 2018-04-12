package techkids.vn.music.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import techkids.vn.music.base.interfaces.IInteractor;
import techkids.vn.music.base.interfaces.IPresenter;
import techkids.vn.music.base.interfaces.IView;

/**
 * Created by HaiLS on 11/04/2018.
 */

public abstract class Presenter<V extends IView, I extends IInteractor>
    implements IPresenter<V, I> {

  protected V mView;
  protected I mInteractor;

  public Presenter() {
    mInteractor = onCreateInteractor();
    mView = onCreateView();
    mView.setPresenter(this);
  }

  @Override
  public Fragment getFragment() {
    return (Fragment) mView;
  }

  @Override
  public I getInteractor() {
    return mInteractor;
  }

  @Override
  public void back() {
    FragmentManager manager = getFragment().getFragmentManager();
    if (manager.getBackStackEntryCount() > 0) {
      manager.popBackStack();
    } else {
      getFragment().getActivity().finish();
    }
  }

}

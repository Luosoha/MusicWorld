package hails.awesome.music.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import hails.awesome.music.activities.BaseActivity;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;

/**
 * Created by HaiLS on 11/04/2018.
 */

public abstract class ViewFragment<P extends IPresenter>
        extends BaseFragment implements IView<P> {
  protected P mPresenter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    if (mRootView == null) {
      mRootView = inflater.inflate(getLayoutId(), container, false);
    }
    ButterKnife.bind(this, mRootView);
    initLayout();
    return mRootView;
  }

  public void initLayout() {

  }

  @Override
  public BaseActivity getBaseActivity() {
    return (BaseActivity) getActivity();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    mPresenter.start();
  }

  @Override
  public void setPresenter(P presenter) {
    mPresenter = presenter;
  }

  @Override
  public P getPresenter() {
    return mPresenter;
  }

}

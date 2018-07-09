package hails.awesome.music.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import hails.awesome.music.utils.DialogUtils;

/**
 * Created by HaiLS on 05/04/2018.
 */

public abstract class BaseFragment extends Fragment {

  protected View mRootView;

  public BaseFragment() {
  }

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    mRootView = inflater.inflate(getLayoutId(), container, false);
    ButterKnife.bind(this, mRootView);
    initLayout();
    return mRootView;
  }

  protected abstract int getLayoutId();

  protected abstract void initLayout();

  protected void showProgress() {
    DialogUtils.showProgressDialog(getActivity());
  }

  protected void hideProgress() {
    DialogUtils.dismissProgressDialog();
  }

  public void onDisplay() {
    Log.d(getClass().getSimpleName(), "onDisplay");
  }

}

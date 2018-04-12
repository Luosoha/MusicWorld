package techkids.vn.music.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import techkids.vn.music.utils.DialogUtils;

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

}

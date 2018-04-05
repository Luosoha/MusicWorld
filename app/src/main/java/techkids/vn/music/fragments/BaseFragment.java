package techkids.vn.music.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import techkids.vn.music.R;
import techkids.vn.music.utils.DialogUtils;

/**
 * Created by HaiLS on 05/04/2018.
 */

public abstract class BaseFragment extends Fragment {

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(getLayoutId(), container, false);
    ButterKnife.bind(this, view);
    initLayout();
    return view;
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

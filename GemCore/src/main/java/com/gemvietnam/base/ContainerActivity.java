package com.gemvietnam.base;

import com.gemvietnam.base.viper.interfaces.ContainerView;
import com.gemvietnam.base.viper.interfaces.IPresenter;
import com.gemvietnam.base.viper.interfaces.IView;
import com.gemvietnam.common.R;
import com.gemvietnam.utils.DialogUtils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Base Fragment
 * Created by neo on 3/22/2016.
 */
public abstract class ContainerActivity extends BaseActivity implements ContainerView {
  /**
   * Return layout resource id for activity
   * Override if you using other layout
   */
  @Override
  public int getLayoutId() {
    return R.layout.container;
  }

  @Override
  public void addView(IView view) {
    if (view instanceof BaseFragment) {
      addFragment(((BaseFragment) view)
              .setAnimIn(R.anim.slide_none)
              .setAnimOut(R.anim.slide_none),
          true);
    }
  }

  @Override
  public void pushView(IView view) {
    if (view instanceof BaseFragment) {
      addFragment(((BaseFragment) view)
              .setAnimIn(R.anim.slide_right_in)
              .setAnimOut(R.anim.slide_right_out),
          true);
    }
  }

  @Override
  public void popView(IView view) {
    if (view instanceof BaseFragment) {
      FragmentManager manager = getSupportFragmentManager();
      Fragment existingFragment = manager.findFragmentByTag(view.getClass().getSimpleName());
      if (existingFragment != null) {
        manager.beginTransaction().remove(existingFragment).commit();
      }
    }
  }

  @Override
  public void pushView(IView view, int frameId) {
    if (view instanceof BaseFragment) {
      addFragment(frameId, (BaseFragment) view, false);
    }
  }

  @Override
  public void loadView(IView view, int frameId) {
    if (view instanceof BaseFragment) {
      addFragment(frameId, (BaseFragment) view, false, true);
    }
  }

  @Override
  public void presentView(IView view) {
    if (view instanceof BaseFragment) {
      addFragment(((BaseFragment) view)
          .setAnimIn(R.anim.slide_bottom_in)
          .setAnimOut(R.anim.slide_bottom_out),
          true);
    }
  }

  @Override
  public void loadChildView(IView view, int frameId, FragmentManager childFragmentManager) {
    addChildFragment(frameId, childFragmentManager, (BaseFragment) view, false, true);
  }

  @Override
  public void pushChildView(IView view, int frameId, FragmentManager childFragmentManager) {
    addChildFragment(frameId, childFragmentManager, (BaseFragment) view, false, false);
  }

  public void addChildFragment(int frameId, FragmentManager childFragmentManager, BaseFragment fragment, boolean addToBackStack, boolean loadExisted) {
    addChildFragment(frameId, childFragmentManager, fragment, addToBackStack, fragment.getClass().getSimpleName(), loadExisted);
  }

  public void addFragment(BaseFragment fragment, boolean addToBackStack) {
    addFragment(fragment, addToBackStack, fragment.getClass().getSimpleName());
  }

  public void addFragment(BaseFragment fragment, boolean addToBackStack, String tag) {
    addFragment(CoreDefault.FRAGMENT_CONTAINER_ID, fragment, addToBackStack, tag);
  }

  @Override
  public void initLayout() {
    addFragment(onCreateFirstFragment(), false);
  }

  @Override
  public void setPresenter(IPresenter presenter) {
    // Nothing to do
  }

  @Override
  public IPresenter getPresenter() {
    return null;
  }

  @Override
  public BaseActivity getBaseActivity() {
    return this;
  }


  @Override
  public Activity getViewContext() {
    return this;
  }

  @Override
  public void back() {
    FragmentManager manager = getSupportFragmentManager();
    if (manager.getBackStackEntryCount() > 0) {
      manager.popBackStack();
    } else {
      finish();
    }
  }

  @Override
  public void backTwiceTimes() {
    FragmentManager manager = getSupportFragmentManager();
    if (manager.getBackStackEntryCount() > 0) {
      manager.popBackStack();
      manager.popBackStack();
    } else {
      finish();
    }
  }

  @Override
  public void onBackPressed() {
    back();
  }

  @Override
  public void showAlertDialog(String message) {
    DialogUtils.showAlert(this, message);
  }

  @Override
  public void showProgress() {
    DialogUtils.showProgressDialog(this);
  }

  @Override
  public void hideProgress() {
    DialogUtils.dismissProgressDialog();
  }

  @Override
  public void onRequestError(String errorCode, String errorMessage) {
    showAlertDialog(errorMessage);
  }

  @Override
  public void showErrorAlert(Context context, String string) {
    showAlertDialog(string);
  }

  @Override
  public void showNetworkErrorDialog(Activity activity) {
    DialogUtils.showNetworkErrorDialog(this);
  }

}

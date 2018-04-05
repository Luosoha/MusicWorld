package com.gemvietnam.base.viper.interfaces;

import android.support.v4.app.FragmentManager;

/**
 * Views can present on a {@link ContainerView}
 * Created by neo on 9/15/2016.
 */
public interface PresentView<P extends IPresenter> extends IView<P> {
  void showProgress();

  void hideProgress();

  void showAlertDialog(String message);

  void onRequestError(String errorCode, String errorMessage);

  void onNetworkError(boolean shouldShowPopup);

  void onRequestSuccess();

  FragmentManager getChildFragmentManager();

  FragmentManager getFragmentManager();

  /**
   * check is top fragment of current activity or not
   */
  boolean isShowing();

  /**
   * check fragment is visible or hidden
   */
  boolean isViewHidden();
}

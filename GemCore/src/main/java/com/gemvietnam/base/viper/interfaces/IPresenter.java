package com.gemvietnam.base.viper.interfaces;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

/**
 * Base Presenter
 * Created by neo on 2/5/2016.
 */
public interface IPresenter<V extends IView, I extends IInteractor> {

  void start();

  V getView();

  I onCreateInteractor();

  V onCreateView();

  I getInteractor();

  Fragment getFragment();

  void presentView();

  void pushView();

  void pushChildView(int frameId, FragmentManager childFragmentManager);

  void loadChildView(int frameId, FragmentManager childFragmentManager);

  Activity getViewContext();

  void registerEventBus();

  void unregisterEventBus();

  void back();

  void onFragmentDisplay();

  boolean isViewShowing();
}

package com.gemvietnam.base.viper.interfaces;


import com.gemvietnam.base.BaseActivity;

import android.app.Activity;

/**
 * Base View
 * Created by neo on 2/5/2016.
 */
public interface IView<P extends IPresenter> {
  void initLayout();

  BaseActivity getBaseActivity();

  Activity getViewContext();

  void setPresenter(P presenter);

  P getPresenter();
}

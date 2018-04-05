package com.gemvietnam.base.viper.interfaces;

/**
 * Base Interactor
 * Created by neo on 8/29/2016.
 */
public interface IInteractor<P extends IPresenter> {
  P getPresenter();
}

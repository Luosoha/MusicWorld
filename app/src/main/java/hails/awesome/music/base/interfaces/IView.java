package hails.awesome.music.base.interfaces;

import hails.awesome.music.activities.BaseActivity;

/**
 * Created by HaiLS on 11/04/2018.
 */

public interface IView<P extends IPresenter> {
  void initLayout();

  void setPresenter(P presenter);

  P getPresenter();

  BaseActivity getBaseActivity();
}


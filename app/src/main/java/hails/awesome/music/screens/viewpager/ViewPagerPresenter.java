package hails.awesome.music.screens.viewpager;

import hails.awesome.music.base.Presenter;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class ViewPagerPresenter extends Presenter<ViewPagerContract.View, ViewPagerContract.Interactor>
    implements ViewPagerContract.Presenter {

  @Override
  public void start() {

  }

  @Override
  public ViewPagerContract.Interactor onCreateInteractor() {
    return new ViewPagerInteractor(this);
  }

  @Override
  public ViewPagerContract.View onCreateView() {
    return new ViewPagerFragment();
  }

}

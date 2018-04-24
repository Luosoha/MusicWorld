package my.awesome.music.screens.viewpager;

import my.awesome.music.base.Interactor;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class ViewPagerInteractor extends Interactor<ViewPagerContract.Presenter>
    implements ViewPagerContract.Interactor {

  public ViewPagerInteractor(ViewPagerContract.Presenter presenter) {
    super(presenter);
  }

}

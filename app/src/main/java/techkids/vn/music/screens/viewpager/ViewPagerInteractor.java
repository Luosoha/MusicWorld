package techkids.vn.music.screens.viewpager;

import techkids.vn.music.base.Interactor;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class ViewPagerInteractor extends Interactor<ViewPagerContract.Presenter>
    implements ViewPagerContract.Interactor {

  public ViewPagerInteractor(ViewPagerContract.Presenter presenter) {
    super(presenter);
  }

}

package hails.awesome.music.screens.playlist;

import hails.awesome.music.activities.BaseActivity;
import hails.awesome.music.base.Presenter;

/**
 * Created by HaiLS on 12/07/2018.
 */

public class PlayListPresenter extends Presenter<PlayListContract.View, PlayListContract.Interactor>
        implements PlayListContract.Presenter {

  public PlayListPresenter(BaseActivity baseActivity) {
    super(baseActivity);
  }

  @Override
  public void start() {

  }

  @Override
  public PlayListContract.Interactor onCreateInteractor() {
    return new PlayListInteractor(this);
  }

  @Override
  public PlayListContract.View onCreateView() {
    return new PlayListFragment();
  }

}

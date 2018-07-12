package hails.awesome.music.screens.playlist;

import hails.awesome.music.base.Interactor;

/**
 * Created by HaiLS on 12/07/2018.
 */

public class PlayListInteractor extends Interactor<PlayListContract.Presenter>
        implements PlayListContract.Interactor {

  public PlayListInteractor(PlayListContract.Presenter presenter) {
    super(presenter);
  }

}

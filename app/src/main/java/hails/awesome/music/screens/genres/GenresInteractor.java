package hails.awesome.music.screens.genres;

import hails.awesome.music.base.Interactor;

/**
 * Created by HaiLS on 11/04/2018.
 */

public class GenresInteractor extends Interactor<GenresContract.Presenter>
    implements GenresContract.Interactor {

  public GenresInteractor(GenresContract.Presenter presenter) {
    super(presenter);
  }
}

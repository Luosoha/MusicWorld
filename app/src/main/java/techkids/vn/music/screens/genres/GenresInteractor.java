package techkids.vn.music.screens.genres;

import techkids.vn.music.base.Interactor;

/**
 * Created by HaiLS on 11/04/2018.
 */

public class GenresInteractor extends Interactor<GenresContract.Presenter>
    implements GenresContract.Interactor {

  public GenresInteractor(GenresContract.Presenter presenter) {
    super(presenter);
  }
}

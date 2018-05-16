package hails.awesome.music.screens.genres;

import hails.awesome.music.R;
import hails.awesome.music.base.Presenter;
import hails.awesome.music.networks.models.Subgenres;
import hails.awesome.music.screens.topsongs.TopSongsPresenter;

/**
 * Created by HaiLS on 11/04/2018.
 */

public class GenresPresenter extends Presenter<GenresContract.View, GenresContract.Interactor>
        implements GenresContract.Presenter {

  @Override
  public void start() {

  }

  @Override
  public GenresContract.Interactor onCreateInteractor() {
    return new GenresInteractor(this);
  }

  @Override
  public GenresContract.View onCreateView() {
    return new GenresFragment();
  }

  @Override
  public void goToTopSongsScreen(Subgenres subgenres) {
    mView.getBaseActivity().pushView(
            R.id.fl_container, new TopSongsPresenter().setSubgenres(subgenres).getFragment(), true
    );
  }

}

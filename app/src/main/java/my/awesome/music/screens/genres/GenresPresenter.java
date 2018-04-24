package my.awesome.music.screens.genres;

import my.awesome.music.R;
import my.awesome.music.base.Presenter;
import my.awesome.music.networks.models.Subgenres;
import my.awesome.music.screens.topsongs.TopSongsPresenter;

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
    mView.getBaseActivity().changeFragment(
            R.id.fl_container, new TopSongsPresenter().setSubgenres(subgenres).getFragment(), true
    );
  }

}

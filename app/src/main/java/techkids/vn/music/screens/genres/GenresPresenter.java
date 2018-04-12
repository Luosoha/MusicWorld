package techkids.vn.music.screens.genres;

import techkids.vn.music.R;
import techkids.vn.music.base.Presenter;
import techkids.vn.music.networks.models.Subgenres;
import techkids.vn.music.screens.topsongs.TopSongsPresenter;

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

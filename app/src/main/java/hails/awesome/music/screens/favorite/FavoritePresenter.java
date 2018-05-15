package hails.awesome.music.screens.favorite;

import java.util.ArrayList;

import hails.awesome.music.R;
import hails.awesome.music.base.Presenter;
import hails.awesome.music.networks.models.Subgenres;
import hails.awesome.music.screens.topsongs.TopSongsPresenter;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class FavoritePresenter extends Presenter<FavoriteContract.View, FavoriteContract.Interactor>
    implements FavoriteContract.Presenter {

  @Override
  public void start() {

  }

  @Override
  public ArrayList<Subgenres> getFavoriteGenres() {
    return mInteractor.getFavoriteGenres(mView.getBaseActivity());
  }

  @Override
  public void goToTopSongScreen(Subgenres subgenres) {
    mView.getBaseActivity().changeFragment(
            R.id.fl_container, new TopSongsPresenter().setSubgenres(subgenres).getFragment(), true
    );
  }

  @Override
  public FavoriteContract.Interactor onCreateInteractor() {
    return new FavoriteInteractor(this);
  }

  @Override
  public FavoriteContract.View onCreateView() {
    return new FavoriteFragment();
  }

}

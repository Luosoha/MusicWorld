package hails.awesome.music.screens.favorite;

import java.util.ArrayList;

import hails.awesome.music.R;
import hails.awesome.music.activities.BaseActivity;
import hails.awesome.music.base.Presenter;
import hails.awesome.music.networks.models.Subgenres;
import hails.awesome.music.screens.topsongs.TopSongsPresenter;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class FavoritePresenter extends Presenter<FavoriteContract.View, FavoriteContract.Interactor>
        implements FavoriteContract.Presenter {

  public FavoritePresenter(BaseActivity baseActivity) {
    super(baseActivity);
  }

  @Override
  public void start() {

  }

  @Override
  public ArrayList<Subgenres> getFavoriteGenres() {
    return mInteractor.getFavoriteGenres(mView.getBaseActivity());
  }

  @Override
  public void goToTopSongScreen(Subgenres subgenres) {
    new TopSongsPresenter(mView.getBaseActivity())
            .setSubgenres(subgenres)
            .setOnBackPressedListener(new TopSongsPresenter.OnBackPressedListener() {
              @Override
              public void onBack() {
                mView.getFavoriteGenres();
              }
            })
            .pushView(R.id.fl_container, true);

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

package techkids.vn.music.screens.genres;

import android.support.v7.widget.RecyclerView;

import techkids.vn.music.R;
import techkids.vn.music.activities.MainActivity;
import techkids.vn.music.adapters.CategoryAdapter;
import techkids.vn.music.base.Presenter;
import techkids.vn.music.fragments.TopsongsFragment;
import techkids.vn.music.networks.models.Subgenres;

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
    MainActivity activity = (MainActivity) ((GenresFragment)mView).getActivity();
    activity.changeFragment(R.id.fl_container, new TopsongsFragment().setSubgenres(subgenres), true);
  }

}

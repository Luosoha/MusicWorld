package hails.awesome.music.screens.genres;

import java.util.ArrayList;

import hails.awesome.music.R;
import hails.awesome.music.base.Presenter;
import hails.awesome.music.managers.RetrofitContext;
import hails.awesome.music.networks.models.SongCategoryResponse;
import hails.awesome.music.networks.models.Subgenres;
import hails.awesome.music.screens.topsongs.TopSongsPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by HaiLS on 11/04/2018.
 */

public class GenresPresenter extends Presenter<GenresContract.View, GenresContract.Interactor>
        implements GenresContract.Presenter {

  private ArrayList<Subgenres> subgenresList = new ArrayList<>();

  @Override
  public void start() {
    getSongCategories();
  }

  private void getSongCategories() {
    mView.getBaseActivity().showProgress();
    subgenresList = mInteractor.getAllSubgenres(mView.getBaseActivity());
    if (subgenresList.isEmpty()) {
      mInteractor.getCategoryList(new Callback<SongCategoryResponse>() {
        @Override
        public void onResponse(Call<SongCategoryResponse> call, Response<SongCategoryResponse> response) {
          ArrayList<Subgenres> songCategories = new ArrayList<>(response.body().getBody().getMap().values());
          for (Subgenres s : songCategories) {
            mInteractor.insertSubgenres(mView.getBaseActivity(), s);
            subgenresList.add(s);
          }
          mView.setupListCategory(subgenresList);
        }

        @Override
        public void onFailure(Call<SongCategoryResponse> call, Throwable t) {
          mView.getBaseActivity().hideProgress();
        }
      });
    } else {
      mView.setupListCategory(subgenresList);
    }
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

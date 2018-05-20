package hails.awesome.music.screens.genres;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;
import hails.awesome.music.networks.models.SongCategoryResponse;
import hails.awesome.music.networks.models.Subgenres;
import retrofit2.Callback;

/**
 * Created by HaiLS on 11/04/2018.
 */

public interface GenresContract {
  interface Interactor extends IInteractor<Presenter> {
    ArrayList<Subgenres> getAllSubgenres(Context context);

    void insertSubgenres(Context context, Subgenres s);

    void getCategoryList(Callback<SongCategoryResponse> callback);
  }

  interface View extends IView<Presenter> {
    void setupListCategory(ArrayList<Subgenres> subgenresList);
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void goToTopSongsScreen(Subgenres subgenres);
  }
}

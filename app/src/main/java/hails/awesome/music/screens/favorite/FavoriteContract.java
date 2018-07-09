package hails.awesome.music.screens.favorite;

import android.content.Context;

import java.util.ArrayList;

import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 13/04/2018.
 */

public interface FavoriteContract {
  interface Interactor extends IInteractor<Presenter> {
    ArrayList<Subgenres> getFavoriteGenres(Context context);
  }

  interface View extends IView<Presenter> {
    void getFavoriteGenres();
  }

  interface Presenter extends IPresenter<View, Interactor> {
    ArrayList<Subgenres> getFavoriteGenres();

    void goToTopSongScreen(Subgenres subgenres);
  }
}

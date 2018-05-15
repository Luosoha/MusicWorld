package hails.awesome.music.screens.favorite;

import android.content.Context;

import java.util.List;

import hails.awesome.music.base.Interactor;
import hails.awesome.music.managers.SQLiteHelper;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class FavoriteInteractor extends Interactor<FavoriteContract.Presenter>
    implements FavoriteContract.Interactor {

  public FavoriteInteractor(FavoriteContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public List<Subgenres> getFavoriteGenres(Context context) {
    return new SQLiteHelper(context).getAllSubgenres();
  }

}

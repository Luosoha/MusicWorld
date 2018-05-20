package hails.awesome.music.screens.genres;

import android.content.Context;

import java.util.ArrayList;

import hails.awesome.music.base.Interactor;
import hails.awesome.music.managers.RetrofitContext;
import hails.awesome.music.managers.SQLiteHelper;
import hails.awesome.music.networks.models.SongCategoryResponse;
import hails.awesome.music.networks.models.Subgenres;
import retrofit2.Callback;

/**
 * Created by HaiLS on 11/04/2018.
 */

public class GenresInteractor extends Interactor<GenresContract.Presenter>
    implements GenresContract.Interactor {

  private SQLiteHelper sqLiteHelper;

  public GenresInteractor(GenresContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public ArrayList<Subgenres> getAllSubgenres(Context context) {
    if (sqLiteHelper == null) {
      sqLiteHelper = new SQLiteHelper(context);
    }
    return sqLiteHelper.getAllSubgenres();
  }

  @Override
  public void insertSubgenres(Context context, Subgenres s) {
    if (sqLiteHelper == null) {
      sqLiteHelper = new SQLiteHelper(context);
    }
    sqLiteHelper.insertSubgenres(s);
  }

  @Override
  public void getCategoryList(Callback<SongCategoryResponse> callback) {
    RetrofitContext.getCategoryList().enqueue(callback);
  }
}

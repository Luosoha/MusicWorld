package hails.awesome.music.screens.topsongs;

import android.content.Context;

import retrofit2.Callback;
import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;
import hails.awesome.music.networks.models.Song;
import hails.awesome.music.networks.models.Subgenres;
import hails.awesome.music.networks.models.TopSongsResponseBody;

/**
 * Created by HaiLS on 12/04/2018.
 */

public interface TopSongsContract {
  interface Interactor extends IInteractor<Presenter> {
    void getTopSongs(String id, Callback<TopSongsResponseBody> callback);

    void saveFavoriteSubgenres(Context context, Subgenres subgenres);
  }

  interface View extends IView<Presenter> {
    void bindData(Subgenres subgenres);

    void bindTopSongs();

    void onSongFound(Song song, String songUrl);
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void getTopSongs(String id);

    void saveFavoriteSubgenres(Subgenres subgenres);

    void getSearchSong(Song song, String keyword);

    void onBackPressed();
  }

}

package hails.awesome.music.screens.mainplayer;

import retrofit2.Callback;
import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;
import hails.awesome.music.networks.models.SearchSongResponseBody;
import hails.awesome.music.networks.models.Song;

/**
 * Created by HaiLS on 12/04/2018.
 */

public interface MainPlayerContract {
  interface Interactor extends IInteractor<Presenter> {
    void getSearchSong(String keyword, Callback<SearchSongResponseBody> callback);
  }

  interface View extends IView<Presenter> {
    void onSongFound(Song song, String songUrl);
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void searchForOnlineSong(Song song);

    void searchForOfflineSong(Song song);

    Song getPreviousSong(Song currentSong);

    Song getNextSong(Song currentSong);
  }
}

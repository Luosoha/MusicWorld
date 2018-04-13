package techkids.vn.music.screens.mainplayer;

import retrofit2.Callback;
import techkids.vn.music.base.interfaces.IInteractor;
import techkids.vn.music.base.interfaces.IPresenter;
import techkids.vn.music.base.interfaces.IView;
import techkids.vn.music.networks.models.SearchSongResponseBody;
import techkids.vn.music.networks.models.Song;

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
    void searchForSong(Song song);

    Song getPreviousSong(Song currentSong);

    Song getNextSong(Song currentSong);
  }
}

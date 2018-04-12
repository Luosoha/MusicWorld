package techkids.vn.music.screens.topsongs;

import retrofit2.Callback;
import techkids.vn.music.base.interfaces.IInteractor;
import techkids.vn.music.base.interfaces.IPresenter;
import techkids.vn.music.base.interfaces.IView;
import techkids.vn.music.networks.models.Song;
import techkids.vn.music.networks.models.Subgenres;
import techkids.vn.music.networks.models.TopSongsResponseBody;

/**
 * Created by HaiLS on 12/04/2018.
 */

public interface TopSongsContract {
  interface Interactor extends IInteractor<Presenter> {
    void getTopSongs(String id, Callback<TopSongsResponseBody> callback);

    void saveFavoriteSubgenres(int position);
  }

  interface View extends IView<Presenter> {
    void bindData(Subgenres subgenres);

    void bindTopSongs();

    void onSongFound(Song song, String songUrl);
  }

  interface Presenter extends IPresenter<View, Interactor> {
    void getTopSongs(String id);

    void saveFavoriteSubgenres(int position);

    void getSearchSong(Song song, String keyword);
  }

}

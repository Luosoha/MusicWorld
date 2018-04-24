package my.awesome.music.screens.mainplayer;

import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import my.awesome.music.R;
import my.awesome.music.activities.BaseActivity;
import my.awesome.music.base.Presenter;
import my.awesome.music.networks.models.SearchSongResponseBody;
import my.awesome.music.networks.models.Song;
import my.awesome.music.utils.ActionHelper;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class MainPlayerPresenter extends Presenter<MainPlayerContract.View, MainPlayerContract.Interactor>
    implements MainPlayerContract.Presenter {

  @Override
  public void start() {

  }

  @Override
  public void searchForSong(final Song song) {
    final BaseActivity activity = mView.getBaseActivity();
    String keyword = song.getName() + " " + song.getArtist();
    activity.showProgress();
    mInteractor.getSearchSong(keyword, new Callback<SearchSongResponseBody>() {
      @Override
      public void onResponse(Call<SearchSongResponseBody> call, Response<SearchSongResponseBody> response) {
        SearchSongResponseBody songs = response.body();
        if (songs != null && !songs.getSongs().isEmpty()) {
          mView.onSongFound(song, songs.getSongUrl());
        } else {
          Toast.makeText(mView.getBaseActivity(), mView.getBaseActivity().getString(R.string.song_not_found_message), Toast.LENGTH_SHORT).show();
        }
        activity.hideProgress();
      }

      @Override
      public void onFailure(Call<SearchSongResponseBody> call, Throwable t) {
        activity.hideProgress();
      }
    });
  }

  @Override
  public Song getPreviousSong(Song currentSong) {
    return Song.SONGS.get(ActionHelper.findPreviousSongPositionOf(currentSong));
  }

  @Override
  public Song getNextSong(Song currentSong) {
    return Song.SONGS.get(ActionHelper.findNextSongPositionOf(currentSong));
  }

  @Override
  public MainPlayerContract.Interactor onCreateInteractor() {
    return new MainPlayerInteractor(this);
  }

  @Override
  public MainPlayerContract.View onCreateView() {
    return new MainPlayerFragment();
  }

}

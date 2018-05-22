package hails.awesome.music.screens.mainplayer;

import android.widget.Toast;

import java.io.File;

import hails.awesome.music.managers.PlayerManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import hails.awesome.music.R;
import hails.awesome.music.activities.BaseActivity;
import hails.awesome.music.base.Presenter;
import hails.awesome.music.networks.models.SearchSongResponseBody;
import hails.awesome.music.networks.models.Song;
import hails.awesome.music.utils.ActionHelper;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class MainPlayerPresenter extends Presenter<MainPlayerContract.View, MainPlayerContract.Interactor>
        implements MainPlayerContract.Presenter {

  private PlayerManager playerManager;

  @Override
  public void start() {
    playerManager = PlayerManager.getInstance();
  }

  @Override
  public void searchForOnlineSong(final Song song) {
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
  public void searchForOfflineSong(Song song) {
    String fileName = song.getName() + "_" + song.getArtist() + ".mp3";
    File musicFile = new File(mView.getBaseActivity().getFilesDir(), fileName);
    mView.onSongFound(song, musicFile.toURI().toString());
  }

  @Override
  public Song getPreviousSong(Song currentSong) {
    return playerManager.getPlayList().get(ActionHelper.findPreviousSongPositionOf(playerManager.getPlayList(), currentSong));
  }

  @Override
  public Song getNextSong(Song currentSong) {
    return playerManager.getPlayList().get(ActionHelper.findNextSongPositionOf(playerManager.getPlayList(), currentSong));
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

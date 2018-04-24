package my.awesome.music.screens.topsongs;

import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import my.awesome.music.managers.PlayerManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import my.awesome.music.R;
import my.awesome.music.activities.BaseActivity;
import my.awesome.music.base.Presenter;
import my.awesome.music.managers.RetrofitContext;
import my.awesome.music.networks.models.SearchSongResponseBody;
import my.awesome.music.networks.models.Song;
import my.awesome.music.networks.models.Subgenres;
import my.awesome.music.networks.models.TopSongsResponseBody;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class TopSongsPresenter extends Presenter<TopSongsContract.View, TopSongsContract.Interactor>
        implements TopSongsContract.Presenter {

  private Subgenres subgenres;
  private ArrayList<Song> playList = new ArrayList<>();

  @Override
  public void start() {
    if (subgenres != null) {
      mView.bindData(subgenres);
    }
  }

  @Override
  public void getTopSongs(String id) {
    playList.clear();
    mView.getBaseActivity().showProgress();
    mInteractor.getTopSongs(id, new Callback<TopSongsResponseBody>() {
      @Override
      public void onResponse(Call<TopSongsResponseBody> call, Response<TopSongsResponseBody> response) {
        TopSongsResponseBody topSongsResponseBody = response.body();
        if (topSongsResponseBody != null) {
          playList = topSongsResponseBody.getSongList().getList();
          PlayerManager.getInstance().setPlayList(playList);
        }
        mView.bindTopSongs(playList);
        mView.getBaseActivity().hideProgress();
      }

      @Override
      public void onFailure(Call<TopSongsResponseBody> call, Throwable t) {
        mView.getBaseActivity().hideProgress();
      }
    });
  }

  @Override
  public void saveFavoriteSubgenres(int position) {
    mInteractor.saveFavoriteSubgenres(position);
  }

  @Override
  public void getSearchSong(final Song song, String keyword) {
    final BaseActivity activity = mView.getBaseActivity();
    activity.showProgress();
    RetrofitContext.getSearchSong(keyword).enqueue(new Callback<SearchSongResponseBody>() {
      @Override
      public void onResponse(Call<SearchSongResponseBody> call, Response<SearchSongResponseBody> response) {
        SearchSongResponseBody songs = response.body();
        if (songs != null && !songs.getSongs().isEmpty()) {
          mView.onSongFound(song, songs.getSongUrl());
        } else {
          Toast.makeText(activity, activity.getString(R.string.song_not_found_message), Toast.LENGTH_SHORT).show();
        }
        mView.getBaseActivity().hideProgress();
      }

      @Override
      public void onFailure(Call<SearchSongResponseBody> call, Throwable t) {
        mView.getBaseActivity().hideProgress();
      }
    });
  }

  public TopSongsPresenter setSubgenres(Subgenres subgenres) {
    this.subgenres = subgenres;
    return this;
  }

  @Override
  public TopSongsContract.Interactor onCreateInteractor() {
    return new TopSongsInteractor(this);
  }

  @Override
  public TopSongsContract.View onCreateView() {
    return new TopSongsFragment();
  }

}

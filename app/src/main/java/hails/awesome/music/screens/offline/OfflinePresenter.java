package hails.awesome.music.screens.offline;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

import hails.awesome.music.base.Presenter;
import hails.awesome.music.managers.PlayerManager;
import hails.awesome.music.networks.models.Song;

public class OfflinePresenter extends Presenter<OfflineContract.View, OfflineContract.Interactor>
        implements OfflineContract.Presenter {


  @Override
  public void start() {
  }

  @Override
  public OfflineContract.Interactor onCreateInteractor() {
    return new OfflineInteractor(this);
  }

  @Override
  public OfflineContract.View onCreateView() {
    return new OfflineFragment();
  }

  @Override
  public ArrayList<Song> getDownloadedSong(Context context) {
    return mInteractor.getDownloadedSong(context);
  }

}

package hails.awesome.music.screens.offline;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;

import hails.awesome.music.base.Presenter;
import hails.awesome.music.managers.PlayerManager;
import hails.awesome.music.networks.models.Song;

public class OfflinePresenter extends Presenter<OfflineContract.View, OfflineContract.Interactor>
        implements OfflineContract.Presenter {

  private PlayerManager playerManager;

  @Override
  public void start() {
    playerManager = PlayerManager.getInstance();
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

  @Override
  public void playSong(Song song) {
    String fileName = song.getName() + "_" + song.getArtist() + ".mp3";
    File musicFile = new File(mView.getBaseActivity().getFilesDir(), fileName);
    playerManager.playNewSong(musicFile.toURI().toString());
  }

}

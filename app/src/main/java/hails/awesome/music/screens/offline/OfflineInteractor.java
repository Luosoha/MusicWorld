package hails.awesome.music.screens.offline;

import android.content.Context;

import java.util.ArrayList;

import hails.awesome.music.base.Interactor;
import hails.awesome.music.managers.SQLiteHelper;
import hails.awesome.music.networks.models.Song;

public class OfflineInteractor extends Interactor<OfflineContract.Presenter>
        implements OfflineContract.Interactor {
  public OfflineInteractor(OfflineContract.Presenter presenter) {
    super(presenter);
  }

  @Override
  public ArrayList<Song> getDownloadedSong(Context context) {
    return new SQLiteHelper(context).getDownloadedSong();
  }
}

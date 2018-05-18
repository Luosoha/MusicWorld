package hails.awesome.music.screens.offline;

import android.content.Context;

import java.util.ArrayList;

import hails.awesome.music.base.interfaces.IInteractor;
import hails.awesome.music.base.interfaces.IPresenter;
import hails.awesome.music.base.interfaces.IView;
import hails.awesome.music.networks.models.Song;

public interface OfflineContract {
  interface Interactor extends IInteractor<Presenter> {
    ArrayList<Song> getDownloadedSong(Context context);
  }

  interface View extends IView<Presenter> {
  }

  interface Presenter extends IPresenter<View, Interactor> {
    ArrayList<Song> getDownloadedSong(Context context);
  }
}

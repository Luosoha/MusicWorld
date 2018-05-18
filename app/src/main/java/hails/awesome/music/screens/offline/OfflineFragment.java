package hails.awesome.music.screens.offline;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import hails.awesome.music.R;
import hails.awesome.music.base.ViewFragment;
import hails.awesome.music.callbacks.OnSongClickListener;
import hails.awesome.music.managers.PlayerManager;
import hails.awesome.music.networks.models.Song;
import hails.awesome.music.screens.topsongs.ListSongAdapter;

public class OfflineFragment extends ViewFragment<OfflineContract.Presenter>
        implements OfflineContract.View, OnSongClickListener {

  @BindView(R.id.offline_song_rv)
  RecyclerView offlineSongRv;

  private ListSongAdapter offlineSongAdapter;
  private ArrayList<Song> offlineSongList;

  @Override
  public void initLayout() {
    super.initLayout();
    offlineSongList = mPresenter.getDownloadedSong(getContext());
    if (!offlineSongList.isEmpty()) {
      setupSongRecyclerView();
      PlayerManager.getInstance().setPlayList(offlineSongList);
    }
  }

  private void setupSongRecyclerView() {
    offlineSongAdapter = new ListSongAdapter(this, offlineSongList);
    offlineSongRv.setLayoutManager(new LinearLayoutManager(getContext()));
    offlineSongRv.setAdapter(offlineSongAdapter);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_offline;
  }

  @Override
  public void onSongClick(Song song) {
    mPresenter.playSong(song);
  }

}

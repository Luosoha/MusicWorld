package hails.awesome.music.screens.offline;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import hails.awesome.music.MusicApplication;
import hails.awesome.music.R;
import hails.awesome.music.base.ViewFragment;
import hails.awesome.music.callbacks.OnMusicPlayerActionListener;
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
  private OnMusicPlayerActionListener onMusicPlayerActionListener;
  private PlayerManager playerManager;

  @Override
  public void initLayout() {
    super.initLayout();
    playerManager = PlayerManager.getInstance();
    onMusicPlayerActionListener = (OnMusicPlayerActionListener) getActivity();
  }

  @Override
  public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    getDownloadedSong();
  }

  public void getDownloadedSong() {
    if (isAdded()) {
      offlineSongList = mPresenter.getDownloadedSong(getContext());
      if (!offlineSongList.isEmpty()) {
        setupSongRecyclerView();
        PlayerManager.getInstance().setPlayList(offlineSongList);
      }
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
    String fileName = song.getName() + "_" + song.getArtist() + ".mp3";
    File musicFile = new File(getBaseActivity().getFilesDir(), fileName);
    playerManager.setCurrentSong(song);
    playerManager.setOfflineMode(true);
    if (onMusicPlayerActionListener != null) {
      onMusicPlayerActionListener.onPlaySong(song, musicFile.toURI().toString(), true);
    }
  }

}

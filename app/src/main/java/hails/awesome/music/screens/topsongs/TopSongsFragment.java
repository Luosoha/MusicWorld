package hails.awesome.music.screens.topsongs;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import hails.awesome.music.R;
import hails.awesome.music.activities.MainActivity;
import hails.awesome.music.adapters.TopSongAdapter;
import hails.awesome.music.base.ViewFragment;
import hails.awesome.music.callbacks.OnMusicPlayerActionListener;
import hails.awesome.music.callbacks.OnTopSongClickListener;
import hails.awesome.music.networks.models.Song;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class TopSongsFragment extends ViewFragment<TopSongsContract.Presenter>
    implements TopSongsContract.View, OnTopSongClickListener {

  private static final int COLUMN_NUMBERS = 1;

  @BindView(R.id.view_back)
  View backView;
  @BindView(R.id.iv_category)
  ImageView categoryIv;
  @BindView(R.id.tv_category_name)
  TextView categoryNameTv;
  @BindView(R.id.rv_top_songs)
  RecyclerView topSongRv;
  @BindView(R.id.view_favorite)
  View favoriteView;
  @BindView(R.id.top_songs_action_btn)
  FloatingActionButton playFabBtn;

  private MainActivity activity;
  private TopSongAdapter topSongAdapter;
  private Subgenres subgenres;
  private int position;
  private OnMusicPlayerActionListener onMusicPlayerActionListener;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_topsongs;
  }

  @Override
  public void initLayout() {
    activity = (MainActivity) getActivity();
    activity.getSupportActionBar().hide();
    onMusicPlayerActionListener = activity;
    addListeners();
  }

  private void addListeners() {
    backView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mPresenter.back();
      }
    });

    favoriteView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mPresenter.saveFavoriteSubgenres(position);
        setFavoriteView();
      }
    });

    playFabBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!Song.SONGS.isEmpty()) {
          onSongClick(Song.SONGS.get(0));
        }
      }
    });
  }

  @Override
  public void bindData(Subgenres subgenres) {
    this.subgenres = subgenres;
    position = getSubgenresPosition();
    mPresenter.getTopSongs(subgenres.getId());
    setupUI();
  }

  private void setupUI() {
    setFavoriteView();
    if (subgenres != null) {
      categoryNameTv.setText(subgenres.getName());
      String src = "genre_" + subgenres.getId();
      int rid = this.categoryIv.getResources().getIdentifier(src,
              "drawable", this.categoryIv.getContext().getPackageName());
      if (rid != 0) {
        Picasso.with(this.getContext()).load(rid).into(categoryIv);
      }
    }

    topSongAdapter = new TopSongAdapter();
    topSongAdapter.setOnTopSongClickListener(this);
    topSongRv.setLayoutManager(new GridLayoutManager(getActivity(), COLUMN_NUMBERS));
    topSongRv.setAdapter(topSongAdapter);
  }

  private void setFavoriteView() {
    if (Subgenres.subgenres.get(position).isFavorite()) {
      favoriteView.setBackgroundResource(R.drawable.ic_favorite_filled_white_24px);
    } else {
      favoriteView.setBackgroundResource(R.drawable.ic_favorite_border_white_24px);
    }
  }

  @Override
  public void onSongClick(final Song song) {
    String keyword = song.getName() + " " + song.getArtist();
    mPresenter.getSearchSong(song, keyword);
  }

  @Override
  public void bindTopSongs() {
    topSongAdapter.notifyDataSetChanged();
  }

  @Override
  public void onSongFound(Song song, String songUrl) {
    if (onMusicPlayerActionListener != null) {
      onMusicPlayerActionListener.onPlaySong(song, songUrl, true);
    }
  }

  public int getSubgenresPosition() {
    for (int i = 0; i < Subgenres.subgenres.size(); i++) {
      if (subgenres.getId().equals(Subgenres.subgenres.get(i).getId())) {
        return i;
      }
    }
    return -1;
  }

  public TopSongsFragment setSubgenres(Subgenres sub) {
    this.subgenres = sub;
    return this;
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    activity.getSupportActionBar().show();
  }

}

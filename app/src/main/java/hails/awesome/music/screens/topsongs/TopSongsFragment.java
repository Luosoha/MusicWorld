package hails.awesome.music.screens.topsongs;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnTouch;
import hails.awesome.music.R;
import hails.awesome.music.activities.MainActivity;
import hails.awesome.music.base.ViewFragment;
import hails.awesome.music.callbacks.OnMusicPlayerActionListener;
import hails.awesome.music.callbacks.OnTopSongClickListener;
import hails.awesome.music.managers.PlayerManager;
import hails.awesome.music.networks.models.Song;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 12/04/2018.
 */

public class TopSongsFragment extends ViewFragment<TopSongsContract.Presenter>
        implements TopSongsContract.View, OnTopSongClickListener {

  @BindView(R.id.view_back)
  View backView;
  @BindView(R.id.iv_category)
  ImageView categoryIv;
  @BindView(R.id.tv_category_name)
  TextView categoryNameTv;
  @BindView(R.id.rv_top_songs)
  RecyclerView topSongRv;
  @BindView(R.id.view_search)
  View searchView;
  @BindView(R.id.view_favorite)
  View favoriteView;
  @BindView(R.id.search_top_song_et)
  EditText searchTopSongEt;
  @BindView(R.id.top_songs_action_btn)
  FloatingActionButton playFabBtn;
  @BindView(R.id.tv_number_of_top_songs)
  TextView numberOfSongTv;

  private MainActivity activity;
  private TopSongAdapter topSongAdapter;
  private Subgenres subgenres;
  private OnMusicPlayerActionListener onMusicPlayerActionListener;

  @OnTouch(R.id.top_songs_fl)
  public boolean onBackGroundTouch() {
    if (searchTopSongEt.isShown()) {
      dismissSearchBar();
    }
    return true;
  }

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
        subgenres.setFavorite(!subgenres.isFavorite());
        mPresenter.saveFavoriteSubgenres(subgenres);
        setFavoriteView();
      }
    });

    playFabBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (!PlayerManager.getInstance().getPlayList().isEmpty()) {
          onSongClick(PlayerManager.getInstance().getPlayList().get(0));
        }
      }
    });

    searchView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (searchTopSongEt.isShown()) {
          dismissSearchBar();
        } else {
          showSearchBar();
        }
      }
    });

    searchTopSongEt.addTextChangedListener(new TextWatcher() {
      @Override
      public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

      }

      @Override
      public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        ArrayList<Song> filterSongList = new ArrayList<>();
        for (Song s : PlayerManager.getInstance().getPlayList()) {
          if (s.getName().toLowerCase().contains(searchTopSongEt.getText().toString().toLowerCase())) {
            filterSongList.add(s);
          }
        }
        topSongAdapter.filterSong(filterSongList);
        numberOfSongTv.setText(String.format(Locale.US, "%d songs", filterSongList.size()));
      }

      @Override
      public void afterTextChanged(Editable editable) {

      }
    });
  }

  private void showSearchBar() {
    searchTopSongEt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_in_from_right));
    searchTopSongEt.setVisibility(View.VISIBLE);
  }

  private void dismissSearchBar() {
    searchTopSongEt.setVisibility(View.INVISIBLE);
    searchTopSongEt.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.anim_out_from_left));
    searchTopSongEt.setText("");
  }

  @Override
  public void bindData(Subgenres subgenres) {
    this.subgenres = subgenres;
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

    topSongAdapter = new TopSongAdapter(this, PlayerManager.getInstance().getPlayList());
    topSongRv.setLayoutManager(new LinearLayoutManager(getActivity()));
    topSongRv.setAdapter(topSongAdapter);
  }

  private void setFavoriteView() {
    if (subgenres.isFavorite()) {
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
    numberOfSongTv.setText(String.format(Locale.US, "%d songs", PlayerManager.getInstance().getPlayList().size()));
    topSongAdapter.notifyDataSetChanged();
  }

  @Override
  public void onSongFound(Song song, String songUrl) {
    if (onMusicPlayerActionListener != null) {
      onMusicPlayerActionListener.onPlaySong(song, songUrl, true);
    }
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

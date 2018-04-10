package techkids.vn.music.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techkids.vn.music.R;
import techkids.vn.music.activities.MainActivity;
import techkids.vn.music.adapters.TopSongAdapter;
import techkids.vn.music.callbacks.OnMusicPlayerActionListener;
import techkids.vn.music.callbacks.OnTopSongClickListener;
import techkids.vn.music.managers.RealmContext;
import techkids.vn.music.managers.RetrofitContext;
import techkids.vn.music.networks.models.SearchSongResponseBody;
import techkids.vn.music.networks.models.Song;
import techkids.vn.music.networks.models.Subgenres;
import techkids.vn.music.networks.models.TopSongsResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopsongsFragment extends BaseFragment implements OnTopSongClickListener {

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

  private TopSongAdapter topSongAdapter;
  private Subgenres sub;
  private int position = -1;
  private OnMusicPlayerActionListener onMusicPlayerActionListener;

  @Override
  public int getLayoutId() {
    return R.layout.fragment_topsongs;
  }

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    for (int i = 0; i < Subgenres.subgenres.size(); i++) {
      if (sub.getId().equals(Subgenres.subgenres.get(i).getId())) {
        position = i;
      }
    }
  }

  @Override
  protected void initLayout() {
    getTopSongs();
    MainActivity activity = (MainActivity) getActivity();
    activity.getSupportActionBar().hide();
    onMusicPlayerActionListener = activity;
    setupUI();
    addListeners();
  }

  private void getTopSongs() {
    Song.SONGS.clear();
    showProgress();
    RetrofitContext.getTopSongs(sub.getId()).enqueue(new Callback<TopSongsResponseBody>() {
      @Override
      public void onResponse(Call<TopSongsResponseBody> call, Response<TopSongsResponseBody> response) {
        TopSongsResponseBody topSongsResponseBody = response.body();
        if (topSongsResponseBody != null) {
          Song.SONGS.addAll(Arrays.asList(topSongsResponseBody.getSongList().getList()));
        }
        topSongAdapter.notifyDataSetChanged();
        hideProgress();
      }

      @Override
      public void onFailure(Call<TopSongsResponseBody> call, Throwable t) {
        hideProgress();
      }
    });
  }

  private void addListeners() {
    backView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });
  }

  private void setupUI() {
    setFavoriteView();

    if (sub != null) {
      categoryNameTv.setText(sub.getName());
      String src = "genre_" + sub.getId();
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

  @OnClick(R.id.view_favorite)
  public void onViewFavoriteClick() {
    RealmContext.getInstance().update(Subgenres.subgenres.get(position), !Subgenres.subgenres.get(position).isFavorite());
    setFavoriteView();
  }

  @OnClick(R.id.top_songs_action_btn)
  public void onFabActionClick() {
    // Play the first song in the list
    // ...

  }

  public void setSubgenres(Subgenres sub) {
    this.sub = sub;
  }

  @Override
  public void onSongClick(final Song song) {
    showProgress();
    String keyword = song.getName() + " " + song.getArtist();
    RetrofitContext.getSearchSong(keyword).enqueue(new Callback<SearchSongResponseBody>() {
      @Override
      public void onResponse(Call<SearchSongResponseBody> call, Response<SearchSongResponseBody> response) {
        SearchSongResponseBody songs = response.body();
        if (songs != null && !songs.getSongs().isEmpty()) {
          if (onMusicPlayerActionListener != null) {
            onMusicPlayerActionListener.onPlaySong(song, songs.getSongUrl(), true);
          }
        } else {
          Toast.makeText(getContext(), getContext().getString(R.string.song_not_found_message), Toast.LENGTH_SHORT).show();
        }
        hideProgress();
      }

      @Override
      public void onFailure(Call<SearchSongResponseBody> call, Throwable t) {
        hideProgress();
      }
    });
  }
}

package techkids.vn.music.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import techkids.vn.music.activities.MainActivity;
import techkids.vn.music.R;
import techkids.vn.music.adapters.TopSongAdapter;
import techkids.vn.music.managers.RealmContext;
import techkids.vn.music.managers.RetrofitContext;
import techkids.vn.music.networks.models.Song;
import techkids.vn.music.networks.models.Subgenres;
import techkids.vn.music.networks.models.TopSongsResponseBody;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopsongsFragment extends BaseFragment {

  private static final String TAG = TopsongsFragment.class.toString();
  private static final int COLUMN_NUMBERS = 1;

  @BindView(R.id.view_back)
  View viewBack;
  @BindView(R.id.iv_category)
  ImageView ivCategory;
  @BindView(R.id.tv_category_name)
  TextView tvCategoryName;
  @BindView(R.id.rv_top_songs)
  RecyclerView rvTopSongs;
  @BindView(R.id.view_favorite)
  View viewFavorite;

  private TopSongAdapter topSongAdapter;

  private Subgenres sub;
  private int position = -1;

  public TopsongsFragment() {
    // Required empty public constructor
  }

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
    ((MainActivity) getActivity()).getSupportActionBar().hide();
    setupUI();
    addListeners();
  }

  private void getTopSongs() {
    Song.SONGS.clear();
    showProgress();
    RetrofitContext.getTopSongs(sub.getId()).enqueue(new Callback<TopSongsResponseBody>() {
      @Override
      public void onResponse(Call<TopSongsResponseBody> call, Response<TopSongsResponseBody> response) {
        Log.d(TAG, "onResponse");
        TopSongsResponseBody topSongsResponseBody = response.body();
        for (Song song : topSongsResponseBody.getSongList().getList()) {
          Song.SONGS.add(song);
        }
        topSongAdapter.notifyDataSetChanged();
        hideProgress();
      }

      @Override
      public void onFailure(Call<TopSongsResponseBody> call, Throwable t) {
        Log.d(TAG, "onFailure");
        hideProgress();
      }
    });
  }

  private void addListeners() {
    viewBack.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        getActivity().onBackPressed();
      }
    });
  }

  private void setupUI() {
    setFavoriteView();

    if (sub != null) {
      tvCategoryName.setText(sub.getName());
      String src = "genre_" + sub.getId();
      int rid = this.ivCategory.getResources().getIdentifier(src,
              "drawable", this.ivCategory.getContext().getPackageName());
      if (rid != 0) {
        Picasso.with(this.getContext()).load(rid).into(ivCategory);
      }
    }

    topSongAdapter = new TopSongAdapter();
//        rvTopSongs.setHasFixedSize(true);
    rvTopSongs.setLayoutManager(new GridLayoutManager(getActivity(), COLUMN_NUMBERS));
    rvTopSongs.setAdapter(topSongAdapter);
  }

  private void setFavoriteView() {
    if (Subgenres.subgenres.get(position).isFavorite()) {
      viewFavorite.setBackgroundResource(R.drawable.ic_favorite_filled_white_24px);
    } else {
      viewFavorite.setBackgroundResource(R.drawable.ic_favorite_border_white_24px);
    }
  }

  @OnClick(R.id.view_favorite)
  public void onViewFavoriteClick() {
    Log.d(TAG, "onClick");
    RealmContext.getInstance().update(Subgenres.subgenres.get(position), !Subgenres.subgenres.get(position).isFavorite());
    setFavoriteView();
  }

  @OnClick(R.id.fab_action)
  public void onFabActionClick() {
    Log.d(TAG, "fab onClick");
    // Play the first song in the list
    // ...

  }

  public void setSub(Subgenres sub) {
    this.sub = sub;
  }

}

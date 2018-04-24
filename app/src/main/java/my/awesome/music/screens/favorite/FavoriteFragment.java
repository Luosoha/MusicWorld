package my.awesome.music.screens.favorite;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import my.awesome.music.R;
import my.awesome.music.base.ViewFragment;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class FavoriteFragment extends ViewFragment<FavoriteContract.Presenter>
        implements FavoriteContract.View {

  @BindView(R.id.lv_playlist)
  ListView playListLv;

  private ArrayList<String> favoriteGenres = new ArrayList<>();
  private ArrayAdapter<String> genreArrayAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_favorite;
  }

  @Override
  public void onStart() {
    super.onStart();
    getFavoriteGenres();
  }

  private void getFavoriteGenres() {
    favoriteGenres.clear();
    favoriteGenres.addAll(mPresenter.getFavoriteGenres());
    if (genreArrayAdapter != null) {
      genreArrayAdapter.notifyDataSetChanged();
    }
  }

  @Override
  public void initLayout() {
    setupUI();
    addListeners();
  }

  private void setupUI() {
    genreArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_play_list, favoriteGenres);
    playListLv.setAdapter(genreArrayAdapter);
  }

  private void addListeners() {
    playListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.goToTopSongScreen(favoriteGenres.get(i));
      }
    });
  }

}

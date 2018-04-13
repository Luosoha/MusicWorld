package techkids.vn.music.screens.favorite;

import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import techkids.vn.music.R;
import techkids.vn.music.base.ViewFragment;

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
  public void onStart() {
    super.onStart();
    favoriteGenres.clear();
    favoriteGenres.addAll(mPresenter.getFavoriteGenres());
    if (genreArrayAdapter != null) {
      genreArrayAdapter.notifyDataSetChanged();
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_play_list;
  }

  @Override
  public void initLayout() {
    setupUI();
  }

  private void setupUI() {
    genreArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_play_list, favoriteGenres);
    playListLv.setAdapter(genreArrayAdapter);
  }

}

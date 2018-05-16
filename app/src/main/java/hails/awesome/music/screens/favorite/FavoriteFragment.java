package hails.awesome.music.screens.favorite;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import hails.awesome.music.R;
import hails.awesome.music.base.ViewFragment;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class FavoriteFragment extends ViewFragment<FavoriteContract.Presenter>
        implements FavoriteContract.View {

  @BindView(R.id.lv_playlist)
  ListView playListLv;

  private ArrayList<Subgenres> favoriteSubgenres = new ArrayList<>();
  private ArrayList<String> favoriteSubgenresName = new ArrayList<>();
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

  public void getFavoriteGenres() {
    favoriteSubgenres.clear();
    favoriteSubgenres.addAll(mPresenter.getFavoriteGenres());
    if (!favoriteSubgenres.isEmpty()) {
      convertSubgenresToString();
    }
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
    convertSubgenresToString();
    genreArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_play_list, favoriteSubgenresName);
    playListLv.setAdapter(genreArrayAdapter);
  }

  private void convertSubgenresToString() {
    favoriteSubgenresName.clear();
    for (Subgenres sub : favoriteSubgenres) {
      favoriteSubgenresName.add(sub.getName());
    }
  }

  private void addListeners() {
    playListLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        mPresenter.goToTopSongScreen(favoriteSubgenres.get(i));
      }
    });
  }

}

package hails.awesome.music.screens.genres;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import hails.awesome.music.R;
import hails.awesome.music.base.ViewFragment;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 11/04/2018.
 */

public class GenresFragment extends ViewFragment<GenresContract.Presenter>
        implements GenresContract.View, CategoryAdapter.OnCategoryClickListener {

  private static final int COLUMN_NUMBERS = 2;

  @BindView(R.id.rv_song_categories)
  RecyclerView songCategoryRv;

  private CategoryAdapter categoryAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_genres;
  }

  @Override
  public void initLayout() {
    setupListCategory();
  }

  private void setupListCategory() {
    if (categoryAdapter == null) {
      categoryAdapter = new CategoryAdapter(this);
    }
    songCategoryRv.setHasFixedSize(true);

    GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), COLUMN_NUMBERS);
    layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
      @Override
      public int getSpanSize(int position) {
        if (position % 3 == 0) {
          return 2;
        } else {
          return 1;
        }
      }
    });

    songCategoryRv.setLayoutManager(layoutManager);
    songCategoryRv.setAdapter(categoryAdapter);
  }

  @Override
  public void onCategoryClick(Subgenres subgenres) {
    mPresenter.goToTopSongsScreen(subgenres);
  }
}

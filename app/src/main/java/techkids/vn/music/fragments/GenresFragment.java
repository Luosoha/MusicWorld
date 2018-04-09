package techkids.vn.music.fragments;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import techkids.vn.music.R;
import techkids.vn.music.activities.BaseActivity;
import techkids.vn.music.activities.MainActivity;
import techkids.vn.music.adapters.CategoryAdapter;
import techkids.vn.music.networks.models.Subgenres;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenresFragment extends BaseFragment implements CategoryAdapter.OnCategoryClickListener {

  private static final int COLUMN_NUMBERS = 2;

  @BindView(R.id.rv_song_categories)
  RecyclerView songCategoryRv;

  private CategoryAdapter categoryAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_genres;
  }

  @Override
  protected void initLayout() {
    setupUI();
  }

  private void setupUI() {
    categoryAdapter = new CategoryAdapter(this);
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
    TopsongsFragment fragment = new TopsongsFragment();
    fragment.setSubgenres(subgenres);
    ((MainActivity) getActivity()).changeFragment(R.id.fl_container, fragment, true);
  }
}

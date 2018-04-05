package techkids.vn.music.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.music.R;
import techkids.vn.music.adapters.CategoryAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class GenresFragment extends Fragment {

  private static final int COLUMN_NUMBERS = 2;
  private static final String TAG = GenresFragment.class.toString();

  @BindView(R.id.rv_song_categories)
  RecyclerView rvSongCategories;

  private CategoryAdapter categoryAdapter;

  public GenresFragment() {
    // Required empty public constructor
  }

  @Override
  public void onStart() {
    super.onStart();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_genres, container, false);

    ButterKnife.bind(this, view);
    setupUI();

    return view;
  }

  private void setupUI() {
    categoryAdapter = new CategoryAdapter();
    rvSongCategories.setHasFixedSize(true);

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

    rvSongCategories.setLayoutManager(layoutManager);
    rvSongCategories.setAdapter(categoryAdapter);
  }

}

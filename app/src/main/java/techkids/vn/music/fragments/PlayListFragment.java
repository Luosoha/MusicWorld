package techkids.vn.music.fragments;


import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import techkids.vn.music.R;
import techkids.vn.music.managers.RealmContext;
import techkids.vn.music.networks.models.Subgenres;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListFragment extends BaseFragment {

  private ArrayList<String> favCategories = new ArrayList<>();
  private ArrayAdapter<String> categoryArrayAdapter;

  @BindView(R.id.lv_playlist)
  ListView playListLv;

  @Override
  public void onResume() {
    super.onResume();
    favCategories.clear();
    List<Subgenres> subs = RealmContext.getInstance().findGenreIsFavor();
    for (Subgenres s : subs) {
      favCategories.add(s.getName());
    }

    if (categoryArrayAdapter != null) {
      categoryArrayAdapter.notifyDataSetChanged();
    }
  }

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_play_list;
  }

  @Override
  protected void initLayout() {
    setupUI();
  }

  private void setupUI() {
    categoryArrayAdapter = new ArrayAdapter<>(getContext(), R.layout.item_play_list, favCategories);
    playListLv.setAdapter(categoryArrayAdapter);
  }

}

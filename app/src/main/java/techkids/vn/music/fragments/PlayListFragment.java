package techkids.vn.music.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.music.R;
import techkids.vn.music.managers.RealmContext;
import techkids.vn.music.networks.json_models.Subgenres;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListFragment extends Fragment {

    private ArrayList<String> favCategories = new ArrayList<>();

    private ArrayAdapter<String> categoryArrayAdapter;

    @BindView(R.id.lv_playlist)
    ListView lvPlayList;

    @Override
    public void onResume() {
        super.onResume();
        favCategories.clear();
        List<Subgenres> subs = RealmContext.getInstance().findGenreIsFavor();
        for (Subgenres s: subs) {
            favCategories.add(s.getTranslationKey());
        }

        if (categoryArrayAdapter != null) {
            categoryArrayAdapter.notifyDataSetChanged();
        }
    }

    public PlayListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);

        ButterKnife.bind(this, view);
        setupUI();

        return view;
    }

    private void setupUI() {
        categoryArrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_play_list, favCategories);
        lvPlayList.setAdapter(categoryArrayAdapter);
    }

}

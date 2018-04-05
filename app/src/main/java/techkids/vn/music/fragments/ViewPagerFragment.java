package techkids.vn.music.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import techkids.vn.music.R;
import techkids.vn.music.adapters.SlideAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewPagerFragment extends Fragment implements View.OnKeyListener {

    private static final String TAG = ViewPagerFragment.class.toString();
    @BindView(R.id.vp_parent)
    ViewPager vpParent;

    @BindView(R.id.tl_title)
    TabLayout tlTitle;

    private PagerAdapter pagerAdapter;

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        ButterKnife.bind(this, view);
        setupUI();
        this.setHasOptionsMenu(true);

        return view;
    }

    private void setupUI() {
        pagerAdapter = new SlideAdapter(getChildFragmentManager());
        vpParent.setAdapter(pagerAdapter);
        tlTitle.setupWithViewPager(vpParent);
        tlTitle.setTabTextColors(Color.BLACK, Color.WHITE);
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            getActivity().finish();
            return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (!menu.hasVisibleItems()) {
            inflater.inflate(R.menu.menu_genres, menu);
        }
    }
}

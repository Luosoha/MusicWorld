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
public class ViewPagerFragment extends BaseFragment implements View.OnKeyListener {

  @BindView(R.id.vp_parent)
  ViewPager vpParent;
  @BindView(R.id.tl_title)
  TabLayout tlTitle;

  private PagerAdapter pagerAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_view_pager;
  }

  @Override
  protected void initLayout() {
    setupUI();
    this.setHasOptionsMenu(true);
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

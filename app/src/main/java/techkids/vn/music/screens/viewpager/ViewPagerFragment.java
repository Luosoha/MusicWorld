package techkids.vn.music.screens.viewpager;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import butterknife.BindView;
import techkids.vn.music.R;
import techkids.vn.music.adapters.SlideAdapter;
import techkids.vn.music.base.ViewFragment;

/**
 * Created by HaiLS on 13/04/2018.
 */

public class ViewPagerFragment extends ViewFragment<ViewPagerContract.Presenter>
    implements ViewPagerContract.View, View.OnKeyListener {

  @BindView(R.id.vp_parent)
  ViewPager viewPager;
  @BindView(R.id.tl_title)
  TabLayout tabLayout;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_view_pager;
  }

  @Override
  public void initLayout() {
    setupUI();
  }

  private void setupUI() {
    this.setHasOptionsMenu(true);
    PagerAdapter pagerAdapter = new SlideAdapter(getChildFragmentManager());
    viewPager.setAdapter(pagerAdapter);
    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setTabTextColors(Color.BLACK, Color.WHITE);
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

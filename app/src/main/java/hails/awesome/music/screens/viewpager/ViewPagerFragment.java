package hails.awesome.music.screens.viewpager;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;

import butterknife.BindView;
import hails.awesome.music.R;
import hails.awesome.music.base.ViewFragment;

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

}

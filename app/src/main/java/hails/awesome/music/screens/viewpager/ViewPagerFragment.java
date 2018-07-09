package hails.awesome.music.screens.viewpager;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
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

  private SlideAdapter slideAdapter;

  @Override
  protected int getLayoutId() {
    return R.layout.fragment_view_pager;
  }

  @Override
  public void initLayout() {
    setupUI();
    addListeners();
  }

  private void setupUI() {
    slideAdapter = new SlideAdapter(getChildFragmentManager(), getBaseActivity());
    viewPager.setAdapter(slideAdapter);
    tabLayout.setupWithViewPager(viewPager);
    tabLayout.setTabTextColors(Color.BLACK, Color.WHITE);
  }

  private void addListeners() {
    tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        if (tab.getPosition() == 1) { // Favorite tab
          slideAdapter.getFavoriteFragment().getFavoriteGenres();
        } else if (tab.getPosition() == 2) { // Offline tab
          slideAdapter.getOfflineFragment().getDownloadedSong();
        }
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
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

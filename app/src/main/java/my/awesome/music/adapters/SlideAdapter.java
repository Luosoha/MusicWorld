package my.awesome.music.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import my.awesome.music.screens.favorite.FavoritePresenter;
import my.awesome.music.screens.genres.GenresPresenter;

/**
 * Created by HaiLS on 05/04/2018.
 */

public class SlideAdapter extends FragmentPagerAdapter {

  private static final int PAGE_NUMBERS = 3;

  public SlideAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new GenresPresenter().getFragment();
      case 1:
        return new FavoritePresenter().getFragment();
      case 2:
        return new GenresPresenter().getFragment();
    }
    return null;
  }

  @Override
  public int getCount() {
    return PAGE_NUMBERS;
  }

  @Override
  public CharSequence getPageTitle(int position) {
    switch (position) {
      case 0:
        return "GENRES";
      case 1:
        return "PLAYLIST";
      case 2:
        return "OFFLINE";
    }
    return null;
  }

}

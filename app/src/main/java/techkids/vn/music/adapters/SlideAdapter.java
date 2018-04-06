package techkids.vn.music.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import techkids.vn.music.fragments.GenresFragment;
import techkids.vn.music.fragments.PlayListFragment;

/**
 * Created by Lush on 1/8/2017.
 */

public class SlideAdapter extends FragmentStatePagerAdapter {

  private static final int PAGE_NUMBERS = 3;

  public SlideAdapter(FragmentManager fm) {
    super(fm);
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return new GenresFragment();
      case 1:
        return new PlayListFragment();
      case 2:
        return new GenresFragment();
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

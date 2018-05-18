package hails.awesome.music.screens.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hails.awesome.music.screens.favorite.FavoriteFragment;
import hails.awesome.music.screens.favorite.FavoritePresenter;
import hails.awesome.music.screens.genres.GenresFragment;
import hails.awesome.music.screens.genres.GenresPresenter;
import hails.awesome.music.screens.offline.OfflineFragment;
import hails.awesome.music.screens.offline.OfflinePresenter;

/**
 * The slide adapter
 */

public class SlideAdapter extends FragmentPagerAdapter {

  private static final int PAGE_NUMBERS = 3;
  private GenresFragment genresFragment;
  private FavoriteFragment favoriteFragment;
  private OfflineFragment offlineFragment;

  public SlideAdapter(FragmentManager fm) {
    super(fm);
    genresFragment = (GenresFragment) new GenresPresenter().getFragment();
    favoriteFragment = (FavoriteFragment) new FavoritePresenter().getFragment();
    offlineFragment = (OfflineFragment) new OfflinePresenter().getFragment();
  }

  @Override
  public Fragment getItem(int position) {
    switch (position) {
      case 0:
        return genresFragment;
      case 1:
        return favoriteFragment;
      case 2:
        return offlineFragment;
    }
    return null;
  }

  public FavoriteFragment getFavoriteFragment() {
    return favoriteFragment;
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
        return "FAVORITE";
      case 2:
        return "OFFLINE";
    }
    return null;
  }

}

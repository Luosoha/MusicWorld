package hails.awesome.music.screens.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import hails.awesome.music.activities.BaseActivity;
import hails.awesome.music.screens.favorite.FavoriteFragment;
import hails.awesome.music.screens.favorite.FavoritePresenter;
import hails.awesome.music.screens.genres.GenresFragment;
import hails.awesome.music.screens.genres.GenresPresenter;
import hails.awesome.music.screens.offline.OfflineFragment;
import hails.awesome.music.screens.offline.OfflinePresenter;
import hails.awesome.music.screens.playlist.PlayListFragment;
import hails.awesome.music.screens.playlist.PlayListPresenter;

/**
 * The slide adapter
 */

public class SlideAdapter extends FragmentPagerAdapter {

  private static final int PAGE_NUMBERS = 4;
  private GenresFragment genresFragment;
  private FavoriteFragment favoriteFragment;
  private OfflineFragment offlineFragment;
  private PlayListFragment playListFragment;

  public SlideAdapter(FragmentManager fm, BaseActivity baseActivity) {
    super(fm);
    genresFragment = (GenresFragment) new GenresPresenter(baseActivity).getFragment();
    favoriteFragment = (FavoriteFragment) new FavoritePresenter(baseActivity).getFragment();
    offlineFragment = (OfflineFragment) new OfflinePresenter(baseActivity).getFragment();
    playListFragment = (PlayListFragment) new PlayListPresenter(baseActivity).getFragment();
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
      case 3:
        return playListFragment;
    }
    return null;
  }

  public FavoriteFragment getFavoriteFragment() {
    return favoriteFragment;
  }

  public OfflineFragment getOfflineFragment() {
    return offlineFragment;
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
      case 3:
        return "PLAYLIST";
    }
    return null;
  }

}

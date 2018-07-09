package hails.awesome.music.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import butterknife.ButterKnife;
import hails.awesome.music.base.BaseFragment;
import hails.awesome.music.utils.DialogUtils;

/**
 * Created by HaiLS on 05/04/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

  private FragmentManager.OnBackStackChangedListener mOnBackStackChangedListener = new FragmentManager.OnBackStackChangedListener() {
    @Override
    public void onBackStackChanged() {
      onFragmentDisplay();
    }
  };

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
    getSupportFragmentManager().addOnBackStackChangedListener(mOnBackStackChangedListener);
  }

  @Override
  protected void onResume() {
    super.onResume();
    onFragmentDisplay();
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    getSupportFragmentManager().removeOnBackStackChangedListener(mOnBackStackChangedListener);
  }

  public void pushView(int containerId, Fragment fragment, boolean addToBackStack) {
    if (addToBackStack) {
      getSupportFragmentManager().beginTransaction()
              .add(containerId, fragment)
              .addToBackStack(null)
              .commit();
    } else {
      getSupportFragmentManager().beginTransaction()
              .add(containerId, fragment)
              .commit();
    }
  }

  public void showProgress() {
    DialogUtils.showProgressDialog(this);
  }

  public void hideProgress() {
    DialogUtils.dismissProgressDialog();
  }

  protected abstract int getLayoutId();

  private void onFragmentDisplay() {
    new Handler().post(new Runnable() {
      @Override
      public void run() {
        Fragment fragment = getTopFragment(getSupportFragmentManager());
        if (fragment instanceof BaseFragment) {
          ((BaseFragment) fragment).onDisplay();
        }
      }
    });
  }

  public static Fragment getTopFragment(FragmentManager manager) {
    if (manager == null) {
      return null;
    }
    if (manager.getBackStackEntryCount() > 0) {
      String fragmentName = manager.getBackStackEntryAt(manager.getBackStackEntryCount() - 1).getName();
      List<Fragment> fragments = manager.getFragments();
      if (fragments != null && !fragments.isEmpty()) {
        Fragment topFragment = null;
        int i = 1;
        while (i < fragments.size() &&
                (topFragment == null || !isSameClass(topFragment, fragmentName))) {

          topFragment = fragments.get(fragments.size() - i);
          i++;
        }
        return topFragment;
      }
    } else {
      List<Fragment> fragments = manager.getFragments();
      if (fragments != null && !fragments.isEmpty()) {
        return fragments.get(0);
      }
    }
    return null;
  }

  private static boolean isSameClass(Fragment topFragment, String fragmentName) {
    String simpleName = topFragment.getClass().getSimpleName();
    return simpleName.equals(fragmentName);
  }

}

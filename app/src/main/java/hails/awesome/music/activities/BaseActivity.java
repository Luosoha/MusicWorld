package hails.awesome.music.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import hails.awesome.music.utils.DialogUtils;

/**
 * Created by HaiLS on 05/04/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
  }

  public void changeFragment(int containerId, Fragment fragment, boolean addToBackStack) {
    if (addToBackStack) {
      getSupportFragmentManager().beginTransaction()
              .replace(containerId, fragment)
              .addToBackStack(null)
              .commit();
    } else {
      getSupportFragmentManager().beginTransaction()
              .replace(containerId, fragment)
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
}

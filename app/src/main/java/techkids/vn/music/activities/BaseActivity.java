package techkids.vn.music.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import techkids.vn.music.R;
import techkids.vn.music.managers.RealmContext;
import techkids.vn.music.utils.DialogUtils;

/**
 * Created by HaiLS on 05/04/2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
    ButterKnife.bind(this);
    RealmContext.init(this);
  }

  @Override
  protected void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  protected void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

  protected void changeFragment(int containerId, Fragment fragment, boolean addToBackStack) {
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

  protected void showProgress() {
    DialogUtils.showProgressDialog(this);
  }

  protected void hideProgress() {
    DialogUtils.dismissProgressDialog();
  }

  protected abstract int getLayoutId();
}

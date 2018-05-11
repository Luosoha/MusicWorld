package hails.awesome.music.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ProgressBar;

import hails.awesome.music.R;

/**
 * Created by HaiLS on 05/04/2018.
 */

public class DialogUtils {
  private static ProgressDialog sProgress;

  public static void showProgressDialog(final Activity activity) {
    try {
      dismissProgressDialog();

      if (sProgress != null && sProgress.isShowing()) {
        sProgress.dismiss();
      } else {
        sProgress = new ProgressDialog(activity);
      }

      sProgress.setMessage(activity.getString(R.string.loading));
      Drawable drawable = new ProgressBar(activity).getIndeterminateDrawable().mutate();
      drawable.setColorFilter(ContextCompat.getColor(activity, R.color.colorPrimaryLight), PorterDuff.Mode.SRC_IN);
      sProgress.setIndeterminateDrawable(drawable);
      sProgress.show();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void dismissProgressDialog() {
    try {
      if (sProgress != null && sProgress.isShowing()) {
        sProgress.dismiss();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

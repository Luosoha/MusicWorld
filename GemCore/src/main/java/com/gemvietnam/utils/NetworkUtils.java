package com.gemvietnam.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Network utilities
 * Created by neo on 3/23/2016.
 */
public class NetworkUtils {

  private NetworkUtils() {

  }

  public static boolean isNoNetworkAvailable(final Activity activity) {
    ConnectivityManager cm =
        (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    return activeNetwork == null ||
        !activeNetwork.isConnectedOrConnecting();
  }

  public static boolean is3GOn(Context context) {
    ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
    if (activeNetwork != null) { // connected to the internet
      if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
        // connected to wifi
        return false;
      } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
        // connected to the mobile provider's data plan
        return true;
      }
    }
    // not connected to the internet
    return false;
  }
}

package com.gemvietnam.utils;

import android.content.Context;
import android.support.annotation.DimenRes;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Dimension Utils
 * Created by neo on 8/15/2016.
 */
public class DimensionUtils {

  private DimensionUtils() {

  }

  /**
   * Convert dp to px
   */
  public static float dpToPx(Context context, float valueInDp) {
    if (context == null || context.getResources() == null) {
      return 0;
    }
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
  }

  public static int getInPx(Context context, @DimenRes int dimenId) {
    return context.getResources().getDimensionPixelSize(dimenId);
  }
}

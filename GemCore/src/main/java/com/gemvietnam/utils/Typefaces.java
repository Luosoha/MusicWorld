package com.gemvietnam.utils;


import com.gemvietnam.base.log.Logger;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * Type face util
 * Created by neo on 3/24/2016.
 */
public class Typefaces {
  private static final String TAG = "Typefaces";

  private static final HashMap<String, Typeface> CACHE = new HashMap<>();

  private Typefaces() {

  }

  public static Typeface get(Context c, String font, String type) {
    synchronized (CACHE) {
      if (!CACHE.containsKey(font)) {
        try {
          String path = "fonts/" + font + "." + type;
          Typeface t = Typeface.createFromAsset(c.getAssets(), path);
          CACHE.put(font, t);
          Logger.d(TAG, "FONT " + t.toString());
          Logger.d(TAG, "FONT " + path);
        } catch (Exception e) {
          Logger.e(TAG, "Could not get typeface '" + font
              + "' because " + e.getMessage(), e);
          return null;
        }
      }
      return CACHE.get(font);
    }
  }
}

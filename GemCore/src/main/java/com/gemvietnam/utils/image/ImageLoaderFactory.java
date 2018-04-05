package com.gemvietnam.utils.image;

/**
 * Image Loader Factory
 * Created by neo on 7/18/2016.
 */
public class ImageLoaderFactory {
  public static final Type DEFAULT = Type.PICASSO;
  private static ImageLoader sInstance;

  public static ImageLoader getInstance() {
    return getInstance(DEFAULT);
  }

  public static ImageLoader getInstance(Type type) {
    if (sInstance == null) {
      if (type == Type.PICASSO) {
        sInstance = new ImageLoaderGlide();
      } else {
        // Use another
      }
    }

    return sInstance;
  }

  public enum Type {
    GLIDE, PICASSO
  }
}

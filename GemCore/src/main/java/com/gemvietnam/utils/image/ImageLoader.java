package com.gemvietnam.utils.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

/**
 * Image Loader interface
 * Created by neo on 7/18/2016.
 */
public interface ImageLoader {
  void loadImage(Context context, String imageUrl, ImageView imageView, int placeHolderId, int errorId, boolean fit);

  void loadImage(Context context, int imageResId, ImageView imageView, int placeHolderId, int errorId, boolean fit);

  void loadImageFromUri(Context context, Uri uri, ImageView imageView);

  Bitmap getBitmapFromUri(Context context, Uri uri);

  void loadImageWithoutPlaceHolder(Context context, String imageUrl, ImageView imageView, boolean fit);

  void pauseLoad(Context context);

  void resumeLoad(Context context);
}

package com.gemvietnam.utils.image;

import com.bumptech.glide.Glide;
import com.gemvietnam.utils.StringUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ImageLoader using Glide
 * Created by neo on 7/18/2016.
 */
public class ImageLoaderGlide implements ImageLoader {
  @Override
  public void loadImage(Context context, String s, ImageView imageView, int placeHolderId, int errorId, boolean fit) {
    String imageUrl = s;
    if (StringUtils.isEmpty(imageUrl)) {
      imageUrl = null;
    }

    if (!fit) {
      Glide
          .with(context)
          .load(imageUrl)
          .placeholder(placeHolderId)
          .error(errorId)
          .centerCrop()
          .dontAnimate()
          .into(imageView);
    } else {
      Glide
          .with(context)
          .load(imageUrl)
          .fitCenter()
          .placeholder(placeHolderId)
          .error(errorId)
          .dontAnimate()
          .into(imageView);
    }
  }

  @Override
  public void loadImage(Context context, int i, ImageView imageView, int placeHolderId, int errorId, boolean fit) {
    int imageResId = i;
    if (imageResId == 0) {
      imageResId = errorId;
    }

    if (!fit) {
      Glide
          .with(context)
          .load(imageResId)
          .placeholder(placeHolderId)
          .error(errorId)
          .centerCrop()
          .dontAnimate()
          .into(imageView);
    } else {
      Glide
          .with(context)
          .load(imageResId)
          .fitCenter()
          .placeholder(placeHolderId)
          .error(errorId)
          .dontAnimate()
          .into(imageView);
    }
  }

  @Override
  public void loadImageFromUri(Context context, Uri u, ImageView imageView) {
    Uri uri = u;
    if (StringUtils.isEmpty(uri.toString())) {
      uri = null;
    }

    Glide
        .with(context)
        .load(uri)
        .dontAnimate()
        .into(imageView);
  }

  @Override
  public Bitmap getBitmapFromUri(Context context, Uri uri) {
    if (context == null || uri == null)
      return null;
    if (StringUtils.isEmpty(uri.toString()))
      return null;
    try {
      return Glide.with(context).load(uri).asBitmap().into(-1, -1).get();
    } catch (InterruptedException e) {
      Logger logger = Logger.getAnonymousLogger();
      logger.log(Level.SEVERE, "an exception was thrown", e);
      Thread.currentThread().interrupt();
      return null;
    } catch (ExecutionException e) {
      Logger logger = Logger.getAnonymousLogger();
      logger.log(Level.SEVERE, " exception was thrown", e);
      return null;
    }
  }

  @Override
  public void loadImageWithoutPlaceHolder(Context context, String s, ImageView imageView, boolean fit) {
    String imageUrl = s;
    if (StringUtils.isEmpty(imageUrl)) {
      imageUrl = null;
    }
    if (!fit) {
      Glide
          .with(context)
          .load(imageUrl)
          .centerCrop()
          .crossFade()
          .dontAnimate()
          .into(imageView);
    } else {
      Glide
          .with(context)
          .load(imageUrl)
          .fitCenter()
          .crossFade()
          .dontAnimate()
          .into(imageView);
    }
  }

  @Override
  public void pauseLoad(Context context) {
    Glide.with(context).pauseRequests();
  }

  @Override
  public void resumeLoad(Context context) {
    Glide.with(context).resumeRequests();
  }
}

package com.gemvietnam.utils.image;

//
//import com.gemvietnam.base.log.Logger;
//import com.gemvietnam.utils.StringUtils;
//import com.squareup.picasso.Picasso;
//
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.net.Uri;
//import android.widget.ImageView;
//
//import java.io.IOException;
//
///**
// * ImageLoader using Picasso
// * Created by neo on 7/18/2016.
// */
//public class ImageLoaderPicasso implements ImageLoader {
//  private static final String TAG = ImageLoaderPicasso.class.getSimpleName();
//
//  @Override
//  public void loadImage(Context context, String imageUrl, ImageView imageView, int placeHolderId, int errorId, boolean fit) {
//    if (StringUtils.isEmpty(imageUrl)) {
//      imageUrl = null;
//    }
//
//    try {
//      imageView.setAdjustViewBounds(true);
//      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//    } catch (Exception e) {
//      Logger.d("Set image attributes for Picasso error " + e.getLocalizedMessage());
////      e.printStackTrace();
//    }
//
//    if (!fit) {
//      Picasso
//          .with(context)
//          .load(imageUrl)
//          .placeholder(placeHolderId)
//          .error(errorId)
//          .tag(TAG)
//          .into(imageView);
//    } else {
//      Picasso
//          .with(context)
//          .load(imageUrl)
//          .placeholder(placeHolderId)
//          .fit()
//          .centerCrop()
//          .error(errorId)
//          .tag(TAG)
//          .into(imageView);
//    }
//  }
//
//  @Override
//  public void loadImage(Context context, int imageResId, ImageView imageView, int placeHolderId, int errorId, boolean fit) {
//    if (imageResId == 0) {
//      imageResId = errorId;
//    }
//
//    try {
//      imageView.setAdjustViewBounds(true);
//      imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//    } catch (Exception e) {
//      Logger.d("Set image attributes for Picasso error " + e.getLocalizedMessage());
//      e.printStackTrace();
//    }
//
//    if (!fit) {
//      Picasso
//          .with(context)
//          .load(imageResId)
//          .placeholder(placeHolderId)
//          .error(errorId)
//          .tag(TAG)
//          .into(imageView);
//    } else {
//      Picasso
//          .with(context)
//          .load(imageResId)
//          .placeholder(placeHolderId)
//          .fit()
//          .centerCrop()
//          .error(errorId)
//          .tag(TAG)
//          .into(imageView);
//    }
//  }
//
//  @Override
//  public void loadImageFromUri(Context context, Uri uri, ImageView imageView) {
//    if (StringUtils.isEmpty(uri.toString())) {
//      uri = null;
//    }
//
//    try {
//      imageView.setAdjustViewBounds(true);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//    Picasso
//        .with(context)
//        .load(uri)
//        .tag(TAG)
//        .into(imageView);
//  }
//
//  @Override
//  public Bitmap getBitmapFromUri(Context context, Uri uri) {
//    if (context == null || uri == null)
//      return null;
//
//    if (StringUtils.isEmpty(uri.toString()))
//      return null;
//
//    try {
//      return Picasso.with(context).load(uri).get();
//    } catch (IOException e) {
//      e.printStackTrace();
//      return null;
//    }
//  }
//
//  @Override
//  public void loadImageWithoutPlaceHolder(Context context, String imageUrl, ImageView imageView, boolean fit) {
//    if (StringUtils.isEmpty(imageUrl)) {
//      imageUrl = null;
//    }
//
//    try {
//      imageView.setAdjustViewBounds(true);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//    if (!fit) {
//      Picasso
//          .with(context)
//          .load(imageUrl)
//          .tag(TAG)
//          .into(imageView);
//    } else {
//      Picasso
//          .with(context)
//          .load(imageUrl)
//          .fit()
//          .centerCrop()
//          .tag(TAG)
//          .into(imageView);
//    }
//  }
//
//  @Override
//  public void pauseLoad(Context context) {
////    Picasso.with(context).pauseTag(TAG);
//  }
//
//  @Override
//  public void resumeLoad(Context context) {
////    Picasso.with(context).resumeTag(TAG);
//  }
//}

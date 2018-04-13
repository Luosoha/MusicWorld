package com.gemvietnam.utils.image;

import com.gemvietnam.base.log.Logger;
import com.gemvietnam.common.R;
import com.gemvietnam.utils.ContextUtils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Image Utils
 * Created by neo on 3/25/2016.
 */
public class ImageUtils {
    private static final String FILE_EXTENSION = ".JPG";
  public static final int IMAGE_QUALITY = 100;
  public static final Bitmap.CompressFormat BITMAP_COMPRESS_FORMAT = Bitmap.CompressFormat.JPEG;
  private static final String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/HVL/";
  private static final String CACHE_FOLDER = ".cache/";

//  private static final Object SCROLL_TAG = new Object();

  private static final int DEFAULT_PLACE_HOLDER = R.color.grey;

  private ImageUtils(){}

  /**
   * Load image with default place holder
   */
  public static void loadImage(Context context, String imageUrl, ImageView imageView) {
    loadImage(context, imageUrl, imageView, DEFAULT_PLACE_HOLDER, DEFAULT_PLACE_HOLDER);
  }

  /**
   * Using Glide to load image
   */
  public static void loadImage(Context context, String imageUrl, ImageView imageView, int placeHolderId, int errorId) {
    loadImage(context, imageUrl, imageView, placeHolderId, errorId, false);
  }

  /**
   * Using Glide to load image
   */
  public static void loadImage(Context context, String imageUrl, ImageView imageView, int placeHolderId, int errorId, boolean fit) {
    if (!ContextUtils.isValidContext(context)) {
      return;
    }

    ImageLoaderFactory.getInstance().loadImage(context, imageUrl, imageView, placeHolderId, errorId, fit);
  }

  /**
   * Using Glide to load image
   */
  public static void loadImage(Context context, int imageResId, ImageView imageView, int placeHolderId, int errorId, boolean fit) {
    if (!ContextUtils.isValidContext(context)) {
      return;
    }

    ImageLoaderFactory.getInstance().loadImage(context, imageResId, imageView, placeHolderId, errorId, fit);
  }

  public static void loadImageFromUri(Context context, Uri uri, ImageView imageView) {
    if (!ContextUtils.isValidContext(context)) {
      return;
    }

    if (imageView == null) {
      return;
    }

    ImageLoaderFactory.getInstance().loadImageFromUri(context, uri, imageView);
  }

  public static Bitmap getBitmapFromUri(Context context, Uri uri) {
    return ImageLoaderFactory.getInstance().getBitmapFromUri(context, uri);
  }

  //Glide
  public static void loadImageWithoutPlaceHolder(Context context, String imageUrl, ImageView imageView, boolean fit) {
    if (!ContextUtils.isValidContext(context)) {
      return;
    }

    ImageLoaderFactory.getInstance().loadImageWithoutPlaceHolder(context, imageUrl, imageView, fit);
  }

  public static void pauseLoad(Context context) {
    ImageLoaderFactory.getInstance().pauseLoad(context);
  }

  public static void resumeLoad(Context context) {
    ImageLoaderFactory.getInstance().resumeLoad(context);
  }

  public static Drawable getDrawable(Context context, int drawableId) {
    Drawable myDrawable;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      myDrawable = context.getDrawable(drawableId);
    } else {
      myDrawable = context.getResources().getDrawable(drawableId);
    }

    return myDrawable;
  }

  public static String getRootPath() {
    File root = new File(ROOT_PATH);
    if (!root.exists()) {
      root.mkdir();
    }

    return ROOT_PATH;
  }

  public static String getCachePath() {
    String cachePath = getRootPath() + CACHE_FOLDER;

    File cache = new File(cachePath);
    if (!cache.exists()) {
      cache.mkdir();
    }

    return cachePath;
  }

  public static Bitmap getBitmapFromLocal(String path) {
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(path, options);
  }

  public static Bitmap getBitmapFromLocal(String path, BitmapFactory.Options options) {
    // final BitmapFactory.Options options = new BitmapFactory.Options();
    // options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(path, options);
  }

  public static String saveCache(Bitmap data) throws IOException {
    String imageName = String.valueOf(System.currentTimeMillis()) + FILE_EXTENSION;
    String pathImage = getCachePath() + imageName;
    saveBitmap(data, pathImage);

    return pathImage;
  }

  public static boolean saveBitmap(Bitmap bitmap, String file) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

    File f = new File(file);

    FileOutputStream fo = null;
    try {
      if (!f.exists())
        if (!f.createNewFile()) {
          Logger.i("Create file not successful");
        } else {
          Logger.i("Create file successful");
        }
      // write the bytes in file
      fo = new FileOutputStream(f);
      fo.write(bytes.toByteArray());

      // remember close de FileOutput
      fo.close();
      Logger.i("Save file success == " + file);
      bitmap.recycle();
      return true;
    } catch (IOException e) {
      Logger.e("Save file Error: \n" + e);
      if (bitmap != null) {
        bitmap.recycle();
      }
    }

    if (fo != null) {
      try {
        fo.close();
      } catch (IOException e) {
        Logger.w(e);
      }
    }

    return false;
  }

  private static int exifToDegrees(int exifOrientation) {
    if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
      return 90;
    } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
      return 180;
    } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
      return 270;
    }
    return 0;
  }

  /**
   * Get a file path from a Uri. This will get the the path for Storage Access
   * Framework Documents, as well as the _data field for the MediaStore and
   * other file-based ContentProviders.
   *
   * @param context The context.
   * @param uri     The Uri to query.
   */
  /**
  public static String getRealPath(final Context context, final Uri uri) {

//        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

    // DocumentProvider
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
        && DocumentsContract.isDocumentUri(context, uri)) {
      // ExternalStorageProvider
      if (isExternalStorageDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        if ("primary".equalsIgnoreCase(type)) {
          return Environment.getExternalStorageDirectory() + "/" + split[1];
        }

        // handle non-primary volumes
      }
      // DownloadsProvider
      else if (isDownloadsDocument(uri)) {

        final String id = DocumentsContract.getDocumentId(uri);
        final Uri contentUri = ContentUris.withAppendedId(
            Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

        return getDataColumn(context, contentUri, null, null);
      }
      // MediaProvider
      else if (isMediaDocument(uri)) {
        final String docId = DocumentsContract.getDocumentId(uri);
        final String[] split = docId.split(":");
        final String type = split[0];

        Uri contentUri = null;
        if ("image".equals(type)) {
          contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        } else if ("video".equals(type)) {
          contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        } else if ("audio".equals(type)) {
          contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        }

        final String selection = "_id=?";
        final String[] selectionArgs = new String[]{
            split[1]
        };

        return getDataColumn(context, contentUri, selection, selectionArgs);
      }
    }
    // MediaStore (and general)
    else if ("content".equalsIgnoreCase(uri.getScheme())) {
      return getDataColumn(context, uri, null, null);
    }
    // File
    else if ("file".equalsIgnoreCase(uri.getScheme())) {
      return uri.getPath();
    }

    return null;
  }
   */

  /**
   * Get the value of the data column for this Uri. This is useful for
   * MediaStore Uris, and other file-based ContentProviders.
   *
   * @param context       The context.
   * @param uri           The Uri to query.
   * @param selection     (Optional) Filter used in the query.
   * @param selectionArgs (Optional) Selection arguments used in the query.
   * @return The value of the _data column, which is typically a file path.
   */
  public static String getDataColumn(Context context, Uri uri, String selection,
                                     String[] selectionArgs) {

    Cursor cursor = null;
    final String column = "_data";
    final String[] projection = {
        column
    };

    try {
      cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
          null);
      if (cursor != null && cursor.moveToFirst()) {
        final int column_index = cursor.getColumnIndexOrThrow(column);
        return cursor.getString(column_index);
      }
    } finally {
      if (cursor != null)
        cursor.close();
    }
    return null;
  }


  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is ExternalStorageProvider.
   */
  public static boolean isExternalStorageDocument(Uri uri) {
    return "com.android.externalstorage.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is DownloadsProvider.
   */
  public static boolean isDownloadsDocument(Uri uri) {
    return "com.android.providers.downloads.documents".equals(uri.getAuthority());
  }

  /**
   * @param uri The Uri to check.
   * @return Whether the Uri authority is MediaProvider.
   */
  public static boolean isMediaDocument(Uri uri) {
    return "com.android.providers.media.documents".equals(uri.getAuthority());
  }

  /**
   * Get Rotate of File. Return 0,90,180,270
   */
  private static int getImageRotation(String filePath) {
    int rotate = 0;
    try {

      ExifInterface exif = new ExifInterface(filePath);
      int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
      switch (orientation) {
        case ExifInterface.ORIENTATION_ROTATE_90:
          rotate = 90;
          break;
        case ExifInterface.ORIENTATION_ROTATE_180:
          rotate = 180;
          break;
        case ExifInterface.ORIENTATION_ROTATE_270:
          rotate = 270;
          break;

      }

    } catch (Exception e) {
      Logger.w(e);
      return rotate;
    }
    return rotate;
  }

  /**
   * Rotate Image to correct.
   * Return: Bitmap
   */
  private static Bitmap rotateImage(Bitmap bitmap, int rotate) {
    Bitmap rotateBitmap;
    if (rotate > 0) {
      Matrix matrix = new Matrix();
      matrix.postRotate(rotate);
      rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    } else {
      rotateBitmap = bitmap;
    }
    return rotateBitmap;
  }

  /**
   * Get content path receive from Gallery
   */
  private static String getRealPathFromURI(Uri contentURI, Context context) {
    String result;
    Cursor cursor = context.getContentResolver().query(contentURI, null, null, null, null);
    if (cursor == null) { // Source is Dropbox or other similar local file path
      result = contentURI.getPath();
    } else {
      cursor.moveToFirst();
      int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
      result = cursor.getString(idx);
      cursor.close();
    }
    return result;
  }
}

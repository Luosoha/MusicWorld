package hails.awesome.music.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import hails.awesome.music.networks.models.Song;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by HaiLS on 15/05/2018.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

  private static String DATABASE_NAME = "TopMusic.db";
  private static int DATABASE_VERSION = 1;

  public SQLiteHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }


  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(Subgenres.SQL_CREATE_SUBGENRES_TABLE);
    sqLiteDatabase.execSQL(Song.SQL_CREATE_SONG_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

  }

  public long insertSubgenres(Subgenres subgenres) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(Subgenres.COLUMN_ID, subgenres.getId());
    values.put(Subgenres.COLUMN_NAME, subgenres.getName());
    values.put(Subgenres.COLUMN_IS_FAVORITE, subgenres.isFavorite());
    return db.insert(Subgenres.TABLE_NAME, null, values);
  }

  public int updateSubgenres(Subgenres subgenres) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(Subgenres.COLUMN_ID, subgenres.getId());
    values.put(Subgenres.COLUMN_NAME, subgenres.getName());
    values.put(Subgenres.COLUMN_IS_FAVORITE, subgenres.isFavorite());
    return db.update(Subgenres.TABLE_NAME, values, Subgenres.COLUMN_ID + " = ?", new String[] {subgenres.getId()});
  }

  public ArrayList<Subgenres> getAllSubgenres() {
    SQLiteDatabase db = this.getReadableDatabase();
    ArrayList<Subgenres> subgenresList = new ArrayList<>();
    Cursor cursor = db.query(
            Subgenres.TABLE_NAME,null, null, null, null, null, null);
    while (cursor.moveToNext()) {
      Subgenres sub = new Subgenres();
      sub.setId(cursor.getString(cursor.getColumnIndex(Subgenres.COLUMN_ID)));
      sub.setName(cursor.getString(cursor.getColumnIndex(Subgenres.COLUMN_NAME)));
      if (cursor.getInt(cursor.getColumnIndex(Subgenres.COLUMN_IS_FAVORITE)) == 0) {
        sub.setFavorite(false);
      } else {
        sub.setFavorite(true);
      }
      subgenresList.add(sub);
    }
    return subgenresList;
  }

  public ArrayList<Subgenres> getFavoriteSubgenres() {
    SQLiteDatabase db = this.getReadableDatabase();
    ArrayList<Subgenres> subgenresList = new ArrayList<>();
    Cursor cursor = db.query(
            Subgenres.TABLE_NAME, null,
            Subgenres.COLUMN_IS_FAVORITE + " = 1",
            null, null, null, null);
    while (cursor.moveToNext()) {
      Subgenres sub = new Subgenres();
      sub.setId(cursor.getString(cursor.getColumnIndex(Subgenres.COLUMN_ID)));
      sub.setName(cursor.getString(cursor.getColumnIndex(Subgenres.COLUMN_NAME)));
      if (cursor.getInt(cursor.getColumnIndex(Subgenres.COLUMN_IS_FAVORITE)) == 0) {
        sub.setFavorite(false);
      } else {
        sub.setFavorite(true);
      }
      subgenresList.add(sub);
    }
    return subgenresList;
  }

  public int deleteAllSubgenres() {
    SQLiteDatabase db = this.getWritableDatabase();
    return db.delete(Subgenres.TABLE_NAME, null, null);
  }

  public long insertSong(Song song) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(Song.COLUMN_NAME, song.getName());
    values.put(Song.COLUMN_ARTIST, song.getArtist());
    values.put(Song.COLUMN_ID_SUBGENRES, song.getSubgenres().getId());
    return db.insert(Song.TABLE_NAME, null, values);
  }

  public ArrayList<Song> getDownloadedSong() {
    SQLiteDatabase db = this.getReadableDatabase();
    ArrayList<Song> downloadedSongList = new ArrayList<>();
    Cursor cursor = db.query(
            Song.TABLE_NAME,null, null, null, null, null, null);
    while (cursor.moveToNext()) {
      Song song = new Song();
      song.setName(cursor.getString(cursor.getColumnIndex(Song.COLUMN_NAME)));
      song.setArtist(cursor.getString(cursor.getColumnIndex(Song.COLUMN_ARTIST)));
      downloadedSongList.add(song);
    }
    return downloadedSongList;
  }

  public boolean isDownloaded(Song song) {
    SQLiteDatabase db = this.getReadableDatabase();
    Cursor cursor = db.query(
            Song.TABLE_NAME,
            null,
            Song.COLUMN_NAME + " = ? AND " + Song.COLUMN_ARTIST + " = ?",
            new String[] {song.getName(), song.getArtist()},
            null, null, null);
    return cursor.moveToFirst();
  }
}


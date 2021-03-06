package hails.awesome.music.managers;

import android.content.Context;

import java.util.List;

import io.realm.Realm;
import hails.awesome.music.networks.models.Subgenres;

/**
 * Created by Lush on 1/8/2017.
 */

public class RealmContext {

  private static RealmContext instance;
  private Realm realm;

  private RealmContext() {
    realm = Realm.getDefaultInstance();
  }

  public List<Subgenres> allSubgenres() {
    return realm.where(Subgenres.class).findAll();
  }

  public void insertSubgenre(Subgenres subgenres) {
    realm.beginTransaction();
    realm.copyToRealm(subgenres);
    realm.commitTransaction();
  }

  public void deleteAll() {
    realm.beginTransaction();
    realm.deleteAll();
    realm.commitTransaction();
  }

  public List<Subgenres> findAllFavoriteGenres() {
    return realm.where(Subgenres.class).equalTo("isFavorite", true).findAll();
  }

  public void update(Subgenres subgenres, boolean favorite) {
    realm.beginTransaction();
    subgenres.setFavorite(favorite);
    realm.commitTransaction();
  }


  public static RealmContext getInstance() {
    return instance;
  }

  public static void init(Context context) {
    Realm.init(context);
    instance = new RealmContext();
  }

}

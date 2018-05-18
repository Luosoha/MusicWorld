package hails.awesome.music;

import android.app.Application;
import android.content.Context;

public class MusicApplication extends Application {
  private static Context context;

  public void onCreate() {
    super.onCreate();
    context = getApplicationContext();
  }

  public static Context getAppContext() {
    return context;
  }
}

package com.waye.plugintestmain;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.didi.virtualapk.PluginManager;

/**
 * Created by Aria.Lao on 2017/9/15.
 */

public class BaseApplication extends Application{

  @Override
  protected void attachBaseContext(Context base) {
    super.attachBaseContext(base);
    long start = System.currentTimeMillis();
    PluginManager.getInstance(base).init();
    Log.d("ryg", "use time:" + (System.currentTimeMillis() - start));
  }
}

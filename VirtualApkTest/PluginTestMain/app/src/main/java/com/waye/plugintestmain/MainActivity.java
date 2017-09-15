package com.waye.plugintestmain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import com.didi.virtualapk.PluginManager;
import com.didi.virtualapk.internal.LoadedPlugin;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
  }

  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.bt_1:
        loadPlugin("com.waye.plugintestmain.plugin", "plugin.apk");
        startPluginActivity("com.waye.plugintestmain.plugin",
            "com.waye.vrplugindemo.PMainActivity");
        break;
      case R.id.bt_2:
        loadPlugin("com.waye.plugin", "plugin2.apk");
        startPluginActivity("com.waye.plugin", "com.waye.plugin.MainActivity");
        break;
    }
  }

  private void startPluginActivity(String pkg, String activityPath) {
    if (PluginManager.getInstance(this).getLoadedPlugin(pkg) == null) {
      Toast.makeText(this, "plugin [com.didi.virtualapk.demo] not loaded", Toast.LENGTH_SHORT)
          .show();
      return;
    }
    Intent intent = new Intent();
    intent.setClassName(this, activityPath);
    startActivity(intent);
  }

  /**
   * 加载插件
   *
   * @param pkg 插件包名
   * @param pluginFile 根目录插件文件名
   */
  private void loadPlugin(String pkg, String pluginFile) {
    PluginManager pluginManager = PluginManager.getInstance(this);
    File apk = new File(Environment.getExternalStorageDirectory(), pluginFile);
    if (apk.exists()) {
      try {
        unInstallPlugin(pkg);
        pluginManager.loadPlugin(apk);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 卸载插件
   *
   * @param pkg 插件包名
   */
  private void unInstallPlugin(String pkg) {
    PluginManager pluginManager = PluginManager.getInstance(this);
    if (pluginManager.getLoadedPlugin(pkg) != null) {
      try {
        Field mPlugins = pluginManager.getClass().getDeclaredField("mPlugins");
        mPlugins.setAccessible(true);
        Map<String, LoadedPlugin> map = (Map<String, LoadedPlugin>) mPlugins.get(pluginManager);
        if (map != null) {
          map.remove(pkg);
        }
      } catch (NoSuchFieldException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
  }
}

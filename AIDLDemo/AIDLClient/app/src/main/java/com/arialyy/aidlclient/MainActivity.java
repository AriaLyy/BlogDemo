package com.arialyy.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.arialyy.aidldemo.Product;
import com.arialyy.aidldemo.LoadMessageInterface;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";
  LoadMessageInterface messageInterface = null;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

  }


  public void onClick(View view){
    ServiceConnection serviceConnection = new ServiceConnection() {
      @Override public void onServiceConnected(ComponentName name, IBinder service) {
        //获取到服务器传过来的对象
        messageInterface = LoadMessageInterface.Stub.asInterface(service);
        try {
          messageInterface.loadMessage("123");
          Product p = new Product();
          p.name = "123";
          p.price = 456;
          messageInterface.loadEntity(p);
        } catch (RemoteException e) {
          e.printStackTrace();
        }
      }

      @Override public void onServiceDisconnected(ComponentName name) {
        Log.e(TAG, "Service has unexpectedly disconnected");
        messageInterface = null;
      }
    };

    //bindService
    Intent intent = new Intent();
    intent.setAction("com.lyy");
    intent.setPackage("com.arialyy.aidldemo");
    //String packageName = "com.arialyy.aidldemo";
    //String className = ".RemoteService";
    //intent.setComponent(new ComponentName(packageName, packageName + className));
    bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
  }

}

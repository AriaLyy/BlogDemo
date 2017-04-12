package com.arialyy.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {
  private static final String TAG = "RemoteService";

  @Override public void onCreate() {
    super.onCreate();

  }

  @Override public IBinder onBind(Intent intent) {
    // Return the interface
    return mBinder;
  }

  private final LoadMessageInterface.Stub mBinder = new LoadMessageInterface.Stub() {

    public int getPid() {
      return android.os.Process.myPid();
    }

    @Override public void loadMessage(String msg) throws RemoteException {
      Log.d(TAG, "msg = " + msg);
    }

    @Override public void loadEntity(Product p) throws RemoteException {
      Log.d(TAG, "entity = " +  p.toString());
    }
  };
}
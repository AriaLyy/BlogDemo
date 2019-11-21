package com.example.sandboxa;

import android.util.Log;
import android.widget.Toast;

public class Util {
    private static final String TAG = "Util";
//    public static String vm = "静态变量";

    public static void invokeStaticeMethod() {
        Log.d(TAG, "我是应用A的invokeStaticeMethod方法");
    }

    public static String getStaticeField() {
        String str = "静态变量vm的值为：" + BaseApp.vm.toString();
        Log.d(TAG, str);
        return str;
    }

    public static void modifyStaticField(String str) {
        BaseApp.vm.append(str);
        Log.d(TAG, "修改VM的数据为：" + BaseApp.vm.toString());
        Log.d(TAG, "classHashCode = " + Util.class.hashCode());
//        Log.d(TAG, "vmHashCode = " + BaseApp.vm.hashCode());
    }

    public static void show(String str) {
        Toast.makeText(BaseApp.app, String.format("应用A ==> %s", str), Toast.LENGTH_SHORT).show();
    }

}

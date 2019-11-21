package com.example.sandboxb;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class MainActivity extends AppCompatActivity {
    private Context appAContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            appAContext = createPackageContext("com.example.sandboxa", CONTEXT_RESTRICTED | CONTEXT_INCLUDE_CODE | CONTEXT_IGNORE_SECURITY);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }



    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.invoke_static:

                try {
                    Class clazz = appAContext.getClassLoader().loadClass("com.example.sandboxa.Util");
                    Method method = clazz.getMethod("getStaticeField");
                    String vm = (String) method.invoke(null);
                    show("vm的数据：" + vm);
                    Log.d("TAG", clazz.hashCode() + "");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.modify_static_field:
                String str = "小红书";
                try {
                    Class clazz = appAContext.getClassLoader().loadClass("com.example.sandboxa.Util");
                    Method method = clazz.getMethod("modifyStaticField", String.class);
                    method.invoke(null, str);
                    Log.d("TAG", clazz.hashCode() + "");
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                show("设置的vm数据为：" + str);
                break;
            case R.id.show_sp:
                SharedPreferences sp = appAContext.getSharedPreferences("sandbox", MODE_PRIVATE);
                show("获取的sp数据为：" + sp.getString("hhh", ""));
                break;
            case R.id.show_db:
                ImageView img = findViewById(R.id.img);
                int id = appAContext.getResources().getIdentifier("hhh", "drawable", appAContext.getPackageName());
                Log.d("TAG", "id = " + id);
                img.setImageDrawable(appAContext.getResources().getDrawable(id));
                break;
            case R.id.show_private_file:
                String temp = readPrivateFile();
                show("读取到到数据为：" + temp);
                break;
        }
    }

    private void show(String str) {
        Toast.makeText(this, "应用B ==> " + str, Toast.LENGTH_SHORT).show();
    }


    private String readPrivateFile() {
        String path = appAContext.getFilesDir().getPath() + "/private_file";
        try {
            FileInputStream fis = new FileInputStream(path);
            byte[] buf = new byte[8096];
            int len = fis.read(buf);
            fis.close();
            return new String(buf, 0, len, "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void openDb(){
    }
}

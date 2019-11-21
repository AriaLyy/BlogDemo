package com.example.sandboxa;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView img = findViewById(R.id.img);
        img.setImageResource(R.drawable.hhh);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.save_static:
                Util.modifyStaticField("xxxx");
                Util.show("修改vm变量数据为：xxxx");
                break;
            case R.id.save_sp:
                SharedPreferences sp = getSharedPreferences("sandbox", MODE_PRIVATE);
                sp.edit().putString("hhh", "你好").commit();
                Util.show("将数据存到sp, key = hhh, value = 你好");
                break;
            case R.id.save_db:
                break;
            case R.id.show_static:
                Util.show("vm的数据为：" + BaseApp.vm.toString());
                break;
            case R.id.save_file:
                Util.show("将字符串：<小太阳> 存到私有文件夹中");
                saveFile("小太阳");
                break;
        }
    }

    private void saveFile(String str){
        File file = new File(getFilesDir().getPath() + "/private_file");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(str.getBytes("UTF-8"));
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

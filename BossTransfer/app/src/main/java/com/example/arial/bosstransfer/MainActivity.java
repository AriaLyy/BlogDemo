package com.example.arial.bosstransfer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.arial.bosstransfer.boss.BossAdapter;
import com.example.arial.bosstransfer.boss.BossEntity;
import com.example.arial.bosstransfer.boss.BossTransferView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String[] mIcons = new String[]{
            "http://static.gaoshouyou.com/i/69/32/35695e1bcd32fca380a43058c3f09993.png",
            "http://static.gaoshouyou.com/i/a8/b2/eda844a551b2c1d69190e8b8fd7d4af8.png",
            "http://static.gaoshouyou.com/i/c8/65/9cc849affe6541bfa7cf7e8855323ce5.png",
            "http://static.gaoshouyou.com/i/c8/65/9cc849affe6541bfa7cf7e8855323ce5.png",
            "http://static.gaoshouyou.com/i/f6/cb/2cf65de5e4cb0e0739b162fa4593d8ed.png"
    };
    private ListView mList;
    private View     mItemView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
        mList = (ListView) findViewById(R.id.list);
        mList.setAdapter(new BossAdapter(this, getData()));
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mItemView = view;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && checkSelfPermission(Settings.ACTION_MANAGE_OVERLAY_PERMISSION) == PackageManager.PERMISSION_GRANTED) {
                    requestAlertWindowPermission();
                } else {
                    TurnHelp.turn(MainActivity.this, findViewById(android.R.id.content), view);
                }

            }
        });
    }

    private void requestAlertWindowPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
            intent.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1);
        }
    }


    private List<BossEntity> getData() {
        List<BossEntity> list = new ArrayList<>();
        int              i    = 0;
        for (String img : mIcons) {
            BossEntity entity = new BossEntity();
            entity.setImg(img);
            entity.setTitle("item = " + i);
            list.add(entity);
            i++;
        }
        return list;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (Settings.canDrawOverlays(this)) {
                TurnHelp.turn(MainActivity.this, findViewById(android.R.id.content), mItemView);
            } else {
                Toast.makeText(this, "申请悬浮框权限失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

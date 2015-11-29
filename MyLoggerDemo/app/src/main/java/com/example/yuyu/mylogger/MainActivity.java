package com.example.yuyu.mylogger;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ordinary:
                printOrdinaryStr();
                break;
            case R.id.json:
                printJson();
                break;
            case R.id.map:
                printMap();
                break;
            case R.id.default_log:
                printSystemDefaultLog();
                break;
        }
    }

    private void printSystemDefaultLog(){
        L.d(TAG, "系统默认格式");
    }

    private void printOrdinaryStr() {
        L.d("带详情信息的Log");
    }

    private void printJson() {
        String json = "{\"name\":\"tom\",\"sex\":\"男\",\"age\":\"24\", \"list\":[1,2,3,4,5],\"end\":\"最后一个\"}";
        L.j(json);
    }

    private void printMap() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 10; i++){
            map.put("key " + i, "value " + i);
        }
        L.m(map);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

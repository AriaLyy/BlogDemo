package com.example.yuyu.slideactivity.demo;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yuyu.slideactivity.R;
import com.example.yuyu.slideactivity.adapter.MyAdapter;
import com.example.yuyu.slideactivity.base.SlideActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyu on 2015/10/26.
 */
public class Activity4 extends SlideActivity {

    RecyclerView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo4);
        init();
    }

    private void init() {
        mList = (RecyclerView) findViewById(R.id.list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mList.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter(getData());
        mList.setAdapter(adapter);
    }

    private List<String> getData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            data.add("item " + i);
        }
        return data;
    }

}

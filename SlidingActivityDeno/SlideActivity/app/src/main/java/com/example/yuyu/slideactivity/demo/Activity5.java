package com.example.yuyu.slideactivity.demo;

import android.graphics.Color;
import android.os.Bundle;

import com.example.yuyu.slideactivity.R;

/**
 * Created by yuyu on 2015/10/29.
 */
public class Activity5 extends TestActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        mRootView.setBackgroundColor(Color.YELLOW);
    }
}

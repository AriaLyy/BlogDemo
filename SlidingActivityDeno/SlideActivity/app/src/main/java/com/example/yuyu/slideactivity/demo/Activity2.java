package com.example.yuyu.slideactivity.demo;

import android.graphics.Color;

import com.example.yuyu.slideactivity.base.BaseActivity;

/**
 * Created by yuyu on 2015/10/26.
 */
public class Activity2 extends BaseActivity {

    @Override
    protected int setBackgroundColor() {
        return Color.YELLOW;
    }

    @Override
    protected String setButtonText() {
        return "第二个Activity";
    }
}

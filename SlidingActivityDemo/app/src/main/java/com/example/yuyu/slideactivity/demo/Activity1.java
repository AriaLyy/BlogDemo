package com.example.yuyu.slideactivity.demo;

import android.graphics.Color;
import android.os.Bundle;

import com.example.yuyu.slideactivity.base.BaseActivity;

/**
 * Created by yuyu on 2015/10/26.
 */
public class Activity1 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCanScrollBack(false);
    }

    @Override
    protected int setBackgroundColor() {
        return Color.BLUE;
    }

    @Override
    protected String setButtonText() {
        return "第一个Activity";
    }


}

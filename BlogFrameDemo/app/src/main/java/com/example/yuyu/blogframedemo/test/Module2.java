package com.example.yuyu.blogframedemo.test;

import android.content.Context;

import com.example.yuyu.blogframedemo.frame.AbsModule;

/**
 * Created by yuyu on 2015/9/6.
 */
public class Module2 extends AbsModule{
    public Module2(Context context) {
        super(context);
    }

    public void module2Test(){
        callback(101, "我是Module22222222");
    }
}

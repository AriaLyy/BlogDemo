package com.example.yuyu.blogframedemo.test;

import android.content.Context;

import com.example.yuyu.blogframedemo.frame.AbsModule;

/**
 * Created by yuyu on 2015/9/6.
 */
public class Module1 extends AbsModule{
    public Module1(Context context) {
        super(context);
    }

    public void module1Test(){
        callback(100, "我是Module1111111");
    }

    public void cusomCallback(){
        callback("myCallback", String.class, "我是Module11的自定义回调......");
    }
}

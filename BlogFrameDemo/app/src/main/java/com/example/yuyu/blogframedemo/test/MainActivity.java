package com.example.yuyu.blogframedemo.test;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.yuyu.blogframedemo.R;
import com.example.yuyu.blogframedemo.frame.AbsActivity;

/**
 * Created by yuyu on 2015/9/6.
 */
public class MainActivity extends AbsActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 回调到自定义的方法
     * @param data
     */
    private void myCallback(String data){
        show(data);
    }

    /**
     * 数据回调，当然你也可以回调到指定的方法里面
     * @param result 返回码，用来判断是哪个接口进行回调
     * @param data   回调数据
     */
    @Override
    protected void dataCallback(int result, Object data) {
        show(String.valueOf(data));
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.module1:
                getModule(this, Module1.class).module1Test();
                break;
            case R.id.module2:
                getModule(this, Module2.class).module2Test();
                break;
            case R.id.custom_module:
                getModule(this, Module1.class).cusomCallback();
                break;
        }
    }

    private void show(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

package com.example.yuyu.slideactivity.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yuyu.slideactivity.R;
import com.example.yuyu.slideactivity.demo.Activity2;
import com.example.yuyu.slideactivity.demo.Activity3;
import com.example.yuyu.slideactivity.demo.Activity4;

public abstract class BaseActivity extends SlideActivity {
    Button mBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        findViewById(R.id.background).setBackgroundColor(setBackgroundColor());
        mBt = (Button) findViewById(R.id.button);
        String str = setButtonText();
        mBt.setText(str);
        mBt.setTag(str.substring(1, 2));
    }

    public void onClick(View view) {
        Intent intent = null;
        switch (String.valueOf(view.getTag())) {
            case "一":
                intent = new Intent(this, Activity2.class);
                break;
            case "二":
                intent = new Intent(this, Activity3.class);
                break;
            case "三":
                intent = new Intent(this, Activity4.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }

    protected abstract int setBackgroundColor();

    protected abstract String setButtonText();

}

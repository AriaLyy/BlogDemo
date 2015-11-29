package com.example.yuyu.slideactivity.demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.example.yuyu.slideactivity.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyu on 2015/10/29.
 */
public class TestActivity extends AppCompatActivity {
    View mRootView;
    private GestureDetector mGestureDetector;
    private static List<TestActivity> mActivitys = new ArrayList<>();
    /**
     * 移动距离
     */
    private float mWindowWidth;
    private TestActivity mBeforeActivity;
    /**
     * 上一个Activity偏移量
     */
    private float mOffsetX;
    /**
     * 上一个页面移出的位置
     */
    private float mOutsideWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        /**
         * 把当前Activity加到列表里面
         */
        mActivitys.add(this);
        initScrollBack();
    }

    /**
     * 初始化左滑退出功能
     */
    private void initScrollBack() {
        mWindowWidth = getWindowManager().getDefaultDisplay().getWidth();
        mOutsideWidth = -mWindowWidth / 4;
        mOffsetX = mOutsideWidth;
        mGestureDetector = new GestureDetector(this, new GestureListener());
        mRootView = getWindow().getDecorView();
        mRootView.setBackgroundColor(Color.BLUE);
    }

    /**
     * 控制分发事件
     */
    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if (ev.getX() < mWindowWidth / 10) {
            if (mActivitys.size() > 1) {
                mBeforeActivity = mActivitys.get(mActivitys.size() - 2);
                beforeActivityTranslationX(mOutsideWidth);
            }
            return onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public void finish() {
        mActivitys.remove(this);
        if (mOffsetX < 0.0001 || mOffsetX > 0.0001) {
            beforeActivityTranslationX(0);
        }
        super.finish();
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, Activity5.class);
        startActivity(intent);
    }

    public View getRootView() {
        return mRootView;
    }

    /**
     * 控制上一个Activity移动
     */
    private void beforeActivityTranslationX(float translationX) {
        if (mBeforeActivity != null) {
            mBeforeActivity.getRootView().setTranslationX(translationX);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * 手势监听
     */
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e1 != null) {
                handlerCurrentActivityScroll(e2);
                handleBeforeActivityScroll(e2, distanceX);
            }
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        /**
         * 处理当前页面滑动
         */
        private void handlerCurrentActivityScroll(MotionEvent e2) {
            mRootView.setTranslationX(e2.getX());
            if (e2.getX() > mWindowWidth - 20) {
                finish();
            }
        }

        /**
         * 处理上一个页面滑动
         */
        private void handleBeforeActivityScroll(MotionEvent e2, float distanceX) {
            if (mBeforeActivity != null) {
                mOffsetX = distanceX < 0 ? mOffsetX + Math.abs(distanceX) / 4 : mOffsetX - Math.abs(distanceX) / 4;
                if (mOffsetX > 0.0001) {
                    mOffsetX = 0f;
                }
                mBeforeActivity.getRootView().setTranslationX(mOffsetX);
            }
        }
    }
}

package com.example.yuyu.slideactivity.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.transition.Slide;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuyu on 2015/10/26.
 */
@TargetApi(Build.VERSION_CODES.LOLLIPOP)
public class SlideActivity extends AppCompatActivity {
    private static final String TAG = "SlideActivity";
    private static List<SlideActivity> mActivitys = new ArrayList<>();
    /**
     * 手势监听
     */
    private GestureDetector mGestureDetector;
    private View mRootView;
    private boolean isScroll = false;
    /**
     * 移动距离
     */
    private float mWindowWidth;
    private SlideActivity mBeforeActivity;
    /**
     * 上一个Activity偏移量
     */
    private float mOffsetX;
    /**
     * 上一个页面移出的位置
     */
    private float mOutsideWidth;
    private boolean canScrollBack = true;
    private boolean canScroll = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Slide(Gravity.RIGHT));
        super.onCreate(savedInstanceState);
        /**
         * 把当前Activity加到列表里面
         */
        mActivitys.add(this);
        initScrollBack();
    }

    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
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

    }

    /**
     * 控制上一个Activity移动
     */
    private void beforeActivityTranslationX(float translationX) {
        if (mBeforeActivity != null) {
            mBeforeActivity.getRootView().setTranslationX(translationX);
        }
    }

    /**
     * 设置是否能滑动
     *
     * @param canScrollBack true 可以滑动
     */
    protected void setCanScrollBack(boolean canScrollBack) {
        this.canScrollBack = canScrollBack;
    }

    public View getRootView() {
        return mRootView;
    }

    @Override
    public void finish() {
        mActivitys.remove(this);
        if (mOffsetX < 0.0001 || mOffsetX > 0.0001) {
            beforeActivityTranslationX(0);
        }
        super.finish();
    }

    /**
     * 控制分发事件
     */
    @Override
    public boolean dispatchTouchEvent(@NonNull MotionEvent ev) {
        if (canScrollBack && ev.getX() < mWindowWidth / 10) {
            if (mActivitys.size() > 1) {
                mBeforeActivity = mActivitys.get(mActivitys.size() - 2);
                beforeActivityTranslationX(mOutsideWidth);
            }
            canScroll = true;
            return onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (canScrollBack && canScroll) {
            if (event.getAction() == MotionEvent.ACTION_UP && isScroll) {
                isScroll = false;
                canScroll = false;
                //退出当前Activity
                if (event.getX() > mWindowWidth / 2) {
                    if (mBeforeActivity != null) {
                        ObjectAnimator.ofFloat(mBeforeActivity.getRootView(), "translationX", mOffsetX, 0).setDuration(500).start();
                    }
                    ObjectAnimator moveIn = ObjectAnimator.ofFloat(mRootView, "translationX", event.getX(), mWindowWidth);
                    moveIn.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            finish();
                        }
                    });
                    moveIn.setDuration(500).start();
                    //反弹回来
                } else if (event.getX() < mWindowWidth / 2) {
                    ObjectAnimator.ofFloat(mRootView, "translationX", event.getX(), 0).setDuration(500).start();
                    if (mBeforeActivity != null) {
                        ObjectAnimator.ofFloat(mBeforeActivity.getRootView(), "translationX", mOffsetX, mOutsideWidth).setDuration(500).start();
                    }
                    mOffsetX = mOutsideWidth;
                }
            } else {
                mGestureDetector.onTouchEvent(event);
            }
        }
        return true;
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
            isScroll = true;
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

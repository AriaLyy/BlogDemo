package com.example.arial.bosstransfer.boss;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.arial.bosstransfer.R;
import com.example.arial.bosstransfer.Util;

/**
 * Created by lyy on 2016/2/19.
 */
@TargetApi(11)
public class BossTransferView extends LinearLayout {
    private static final String TAG = "ExpansionTemp";
    private View mHandleView;
    private WindowManager mWm;
    private ImageView mImg;
    private View mTemp;
    //    private ProgressBar mPb;
    private ImageView mPb;
    private int[] mLocation = new int[2];
    private View mRootView;

    public BossTransferView(Context context, View rootView, View handleView, WindowManager wm) {
        super(context, null);
        mHandleView = handleView;
        mRootView = rootView;
        mWm = wm;
        init();
    }

    public BossTransferView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public BossTransferView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_boss_transfer, this);
        mImg = (ImageView) findViewById(R.id.img);
        mTemp = findViewById(R.id.line);
//        mPb = (ProgressBar) findViewById(R.id.progress);
        mPb = (ImageView) findViewById(R.id.progress);
        mHandleView.getLocationInWindow(mLocation);
        int sbh = Util.getStatusBarHeight(getContext());
        mImg.setTranslationY(mLocation[1] - sbh);
        mTemp.setTranslationY(mLocation[1] + mImg.getMeasuredHeight() / 2 + sbh);
        mPb.setVisibility(GONE);
        Bitmap bm = getViewImg(mHandleView);
        if (bm != null) {
            mImg.setImageBitmap(getViewImg(mHandleView));
        }
        AnimationDrawable ad = new AnimationDrawable();
        ad.addFrame(getDrawable(R.mipmap.icon_refresh_left), 200);
        ad.addFrame(getDrawable(R.mipmap.icon_refresh_center), 200);
        ad.addFrame(getDrawable(R.mipmap.icon_refresh_right), 200);
        mPb.setImageDrawable(ad);
        ad.setOneShot(false);
        ad.start();
    }

    private Drawable getDrawable(@DrawableRes int drawable){
        return getContext().getResources().getDrawable(drawable);
    }

    public void show() {
        handleRootView();
        setBackgroundColor(Color.parseColor("#7f000000"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTemp.setVisibility(View.VISIBLE);
                expansion();
            }
        }, 500);
    }

    private void handleRootView() {
        ObjectAnimator setScaleY = ObjectAnimator.ofFloat(mRootView, "scaleY", 1f, 0.95f);
        ObjectAnimator setScaleX = ObjectAnimator.ofFloat(mRootView, "scaleX", 1f, 0.95f);
        AnimatorSet set = new AnimatorSet();
        set.play(setScaleX).with(setScaleY);
        set.setDuration(500);
        set.start();
    }

    public Bitmap getViewImg(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        int width = Util.getScreenParams(getContext())[0];
        Bitmap bmp = view.getDrawingCache();
        if (bmp == null) {
            return null;
        }
        Bitmap bp;
        bp = Bitmap.createBitmap(bmp, 0, 0, width, bmp.getHeight());
        view.destroyDrawingCache();
        return bp;
    }

    /**
     * 扩展到整个屏幕
     */
    private void expansion() {
        int wh = Util.getScreenParams(getContext())[1];
        int sbh = Util.getStatusBarHeight(getContext());
        int h = Math.max(mLocation[1], Math.abs(mLocation[1] - wh));
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTemp, "scaleY", 1f, h + sbh);
        animator.setDuration(500);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mImg.setVisibility(View.GONE);
                Intent intent = new Intent(getContext(), BossDetailActivity.class);
                getContext().startActivity(intent);
                mRootView.setScaleY(1f);
                mRootView.setScaleX(1f);
                mPb.setVisibility(VISIBLE);
                setBackgroundColor(Color.TRANSPARENT);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        fade();
                    }
                }, 1000);
            }
        });
        animator.start();
    }

    /**
     * 淡出
     */
    private void fade() {
        mPb.setVisibility(GONE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(mTemp, "alpha", 1f, 0f);
        animator.setDuration(800);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTemp.setVisibility(GONE);
//                mTemp.setScaleY(1f);
                mPb.setVisibility(GONE);
                mWm.removeView(BossTransferView.this);
            }
        });
        animator.start();
    }
}

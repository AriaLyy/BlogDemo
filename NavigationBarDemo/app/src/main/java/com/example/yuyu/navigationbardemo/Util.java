package com.example.yuyu.navigationbardemo;

import android.content.res.Resources;

/**
 * Created by yuyu on 2015/12/17.
 */
public class Util {
    private static final float DENSITY = Resources.getSystem().getDisplayMetrics().density;

    public static int dp2px(int dp) {
        return Math.round(dp * DENSITY);
    }
}

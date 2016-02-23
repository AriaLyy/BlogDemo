package com.example.arial.bosstransfer;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by AriaL on 2016/2/22.
 */
public class Util {
    /**
     * 获取屏幕参数，这个获取到的高是包含导航栏和状态栏的
     *
     * @param context
     * @return
     */
    public static int[] getScreenParams(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return new int[]{wm.getDefaultDisplay().getWidth(), wm.getDefaultDisplay().getHeight()};
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}

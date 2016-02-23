package com.example.arial.bosstransfer;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;

import com.example.arial.bosstransfer.boss.BossTransferView;

/**
 * Created by AriaL on 2016/2/23.
 */
public class TurnHelp {
    /**
     * 带动画跳转详情页面
     */
    public static void turn(Context context, View rootView, View itemView) {
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams wmParams;
        BossTransferView temp = new BossTransferView(context, rootView, itemView, wm);
        wmParams = new WindowManager.LayoutParams();
        wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;     // 系统提示类型,重要
        wmParams.format = 1;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; // 不能抢占聚焦点
        wmParams.flags = wmParams.flags | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        wmParams.flags = wmParams.flags | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS; // 排版不受限制
        wmParams.flags = wmParams.flags | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL; // 排版不受限制
        wmParams.alpha = 1.0f;
        wmParams.gravity = Gravity.LEFT | Gravity.TOP;   //调整悬浮窗口至左上角
        //以屏幕左上角为原点，设置x、y初始值
        wmParams.x = 0;
        wmParams.y = 0;
        //设置悬浮窗口长宽数据
        wmParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        wmParams.height = WindowManager.LayoutParams.MATCH_PARENT;
        //显示myFloatView图像
        wm.addView(temp, wmParams);
        temp.show();
    }
}

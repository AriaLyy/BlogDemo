package com.example.yuyu.blogframedemo.frame;

import android.content.Context;

public class AbsModule {
    public String TAG = "";
    private Context mContext;
    private ModuleListener mModuleListener;

    public AbsModule(Context context) {
        mContext = context;
        init();
    }

    /**
     * 初始化一些东西
     */
    private void init() {
        String className = getClass().getName();
        String arrays[] = className.split("\\.");
        TAG = arrays[arrays.length - 1];
    }

    public void setModuleListener(ModuleListener moduleListener) {
        if (moduleListener == null)
            throw new NullPointerException("ModuleListener为空");
        this.mModuleListener = moduleListener;
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 统一的回调
     *
     * @param result 返回码
     * @param data   回调数据
     */
    protected void callback(int result, Object data) {
        mModuleListener.callback(result, data);
    }

    /**
     * module回调
     *
     * @param method 回调的方法名
     */
    @Deprecated
    protected void callback(String method) {
        mModuleListener.callback(method);
    }

    /**
     * 带参数的module回调
     *
     * @param method        回调的方法名
     * @param dataClassType 回调数据类型
     * @param data          回调数据
     */
    @Deprecated
    protected void callback(String method, Class<?> dataClassType, Object data) {
        mModuleListener.callback(method, dataClassType, data);
    }
}

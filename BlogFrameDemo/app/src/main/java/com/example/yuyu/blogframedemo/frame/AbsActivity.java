package com.example.yuyu.blogframedemo.frame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public abstract class AbsActivity extends AppCompatActivity {
    private static IOCProxy mProxy;
    private IOCProxy mNewProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mNewProxy = IOCProxy.newInstance(this, null);
    }

    /**
     * 利用反射来对代理进行重指向
     */
    private void setProxy() {
        mProxy = mNewProxy;
    }

    /**
     * 获取Module
     */
    protected static <T extends AbsModule> T getModule(AbsActivity activity, Class<T> clazz) {
        Method m = ReflectionUtil.getMethod(activity.getClass(), "setProxy", new Class[]{});
        try {
            m.invoke(activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        T module = ModuleFactory.getModule(activity, clazz);
        mProxy.changeModule(module);
        return module;
    }

    /**
     * 统一的回调接口
     *
     * @param result 返回码，用来判断是哪个接口进行回调
     * @param data   回调数据
     */
    protected abstract void dataCallback(int result, Object data);
}

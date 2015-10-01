package com.example.yuyu.blogframedemo.frame;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by lyy on 2015/7/30.
 * 反射工具类
 */
public class ReflectionUtil {
    private static final String TAG = "ReflectionUtil";
    private static final String ID = "$id";
    private static final String LAYOUT = "$layout";
    private static final String STYLE = "$style";
    private static final String STRING = "$string";
    private static final String DRAWABLE = "$drawable";
    private static final String ARRAY = "$array";

    /**
     * 获取类里面的指定对象，如果该类没有则从父类查询
     */
    public static Field getField(Class clazz, String name) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(name);
        } catch (NoSuchFieldException e) {
            try {
                field = clazz.getField(name);
            } catch (NoSuchFieldException e1) {
                if (clazz.getSuperclass() == null) {
                    return field;
                } else {
                    field = getField(clazz.getSuperclass(), name);
                }
            }
        }
        if (field != null){
            field.setAccessible(true);
        }
        return field;
    }

    /**
     * 利用递归找一个类的指定方法，如果找不到，去父亲里面找直到最上层Object对象为止。
     *
     * @param clazz      目标类
     * @param methodName 方法名
     * @param params     方法参数类型数组
     * @return 方法对象
     */
    public static Method getMethod(Class clazz, String methodName, final Class[] params) {
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(methodName, params);
        } catch (NoSuchMethodException e) {
            try {
                method = clazz.getMethod(methodName, params);
            } catch (NoSuchMethodException ex) {
                if (clazz.getSuperclass() == null) {
                    return method;
                } else {
                    method = getMethod(clazz.getSuperclass(), methodName, params);
                }
            }
        }
        if (method != null) {
            method.setAccessible(true);
        }
        return method;
    }

}

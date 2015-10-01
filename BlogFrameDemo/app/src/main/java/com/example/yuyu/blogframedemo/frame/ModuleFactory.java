package com.example.yuyu.blogframedemo.frame;

import android.content.Context;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ModuleFactory {

    private static final String TAG = "ModuleFactory";

    private static Map<String, AbsModule> mModules = new HashMap<>();

    /**
     * 获取Module
     */
    protected static <T extends AbsModule> T getModule(Context context, Class<T> clazz) {
        T module = (T) mModules.get(String.valueOf(clazz.hashCode()));
        if (module == null) {
            return newInstanceModule(context, clazz);
        }
        return module;
    }

    /**
     * 构造一个新的Module
     */
    private static <T extends AbsModule> T newInstanceModule(Context context, Class<T> clazz) {
        Class[] paramTypes = {Context.class};
        Object[] params = {context};
        try {
            Constructor<T> con = clazz.getConstructor(paramTypes);
            T module = con.newInstance(params);
            mModules.put(String.valueOf(clazz.hashCode()), module);
            return module;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}

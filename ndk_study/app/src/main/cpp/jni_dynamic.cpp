//
// Created by aria on 2019/4/12.
//
#include <jni.h>
#include "native-lib.cpp"
#include <string>

/**
 * 指定需要动态注册的类，需要注意的是，一个.so中只能有一个JNI_OnLoad
 */
static const char *jniClassName = "com/example/ndkstudy/Util";
static const char *jniClassName1 = "com/example/ndkstudy/Util1";
/**
 * jni函数和c函数的对应关系
 */
static JNINativeMethod methods[] = {
        {"stringFromJNI", "()Ljava/lang/String;", (void *) stringFromJNI},
};
static JNINativeMethod methods1[] = {
        {"test", "()Ljava/lang/String;", (void *) jniTest},
};

/**
 * 注册方法，注册com/example/ndkstudy/Util类中所有的jni函数
 * @param env
 * @return
 */
static int registerUtil(JNIEnv *env) {
    jclass clazz = env->FindClass(jniClassName);
    if (clazz == nullptr)
        return JNI_FALSE;

    jint methodSize = sizeof(methods) / sizeof(methods[0]);
    if (env->RegisterNatives(clazz, methods, methodSize) < 0)
        return JNI_FALSE;

    return JNI_TRUE;
}

static int registerUtil1(JNIEnv *env) {
    jclass clazz = env->FindClass(jniClassName1);
    if (clazz == nullptr)
        return JNI_FALSE;

    jint methodSize = sizeof(methods1) / sizeof(methods1[0]);
    if (env->RegisterNatives(clazz, methods1, methodSize) < 0)
        return JNI_FALSE;

    return JNI_TRUE;
}

/**
 * System.loadLibrary 加载完 JNI 动态库之后，调用 JNI_OnLoad 函数，开始动态注册
 * 需要注意的是，一个.so中只能有一个JNI_OnLoad
 */
JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = nullptr;
    jint result = -1;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK)
        return JNI_ERR;

    //注册方法
    registerUtil(env);
    registerUtil1(env);
//    if (!registerNatives1(env))
//    if (!registerNatives(env) && !registerNatives1(env))
//        return JNI_ERR;


    result = JNI_VERSION_1_6;
    return result;
}

/**
 * 当 VM 释放该组件时会调用 JNI_OnUnload 方法
 * @param vm
 * @param reserved
 */
JNIEXPORT void JNICALL JNI_OnUnload(JavaVM *vm, void *reserved) {
    JNIEnv *env = nullptr;
    jint ret = vm->GetEnv(reinterpret_cast<void **>(&env), JNI_VERSION_1_6);
    if (ret != JNI_OK) {
        return;
    }
}




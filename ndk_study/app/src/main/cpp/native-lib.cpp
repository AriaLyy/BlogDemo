//#include <jni.h>
//#include <string>

//
//extern "C" JNIEXPORT jstring JNICALL Java_com_example_ndkstudy_Util_stringFromJNI(
//        JNIEnv *env,
//        jobject obj) {
//    std::string hello = "Hello from C++";
//    return env->NewStringUTF(hello.c_str());
//}

#include <jni.h>
#include <string>
//#include "native-lib.h"

extern jstring stringFromJNI(JNIEnv *env, jobject obj) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

extern jstring jniTest(JNIEnv *env, jobject obj){
    std::string test = "test";
    return env->NewStringUTF(test.c_str());
}
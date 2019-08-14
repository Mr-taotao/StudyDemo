//
// Created by chtlei on 18-10-18.
//
#include "com_example_chtlei_mydemo_jnitest_JNIUtils.h"
//#include <jni.h>
#include "test.h"

JNIEXPORT jstring JNICALL Java_com_example_chtlei_mydemo_jnitest_JNIUtils_stringFromJNI(JNIEnv *env, jclass jclass1) {
    return env -> NewStringUTF("Hello, I'm from jni");
  }

JNIEXPORT jstring JNICALL Java_com_example_chtlei_mydemo_jnitest_JNIUtils_encryptString(JNIEnv *env, jclass jclass1, jstring param) {
    char *target = (char *) env -> GetStringUTFChars(param,0);
    char *result = encode(target);
    return env -> NewStringUTF(result);
}





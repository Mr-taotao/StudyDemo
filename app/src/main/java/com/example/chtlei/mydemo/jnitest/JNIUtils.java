package com.example.chtlei.mydemo.jnitest;

/**
 * Created by chtlei on 18-10-18.
 */

public class JNIUtils {
    // 加载native-jni
    static {
        System.loadLibrary("native-jni");
    }
    //java调C中的方法都需要用native声明且方法名必须和c的方法名一样
    public static native String stringFromJNI();
    public static native String encryptString(String param);
}

package com.example.chtlei.jnitestdemo;

/**
 * Created by chtlei on 18-11-5.
 */

public class MainActivity {

    static {
        System.loadLibrary("native-lib");
    }

    public static native String stringFromJNI();
}

package com.example.chtlei.mydemo.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Created by chtlei on 19-4-29.
 */

public class AndroidToJs extends Object {
    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void back(String msg) {

        Log.i("LCT","JS调用了Android的hello方法: " + msg);

    }
}

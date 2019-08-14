package com.example.chtlei.mydemo.proxy;

import android.util.Log;

/**
 * Created by chtlei on 19-7-12.
 */

public class RealSubject implements ProxyInterface {
    @Override
    public void request() {
        Log.i("RealSubject","RealSubject request execute");
    }
}

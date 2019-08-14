package com.example.chtlei.mydemo.proxy;

import android.util.Log;

/**
 * Created by chtlei on 19-7-12.
 */

public class ProxySubject implements ProxyInterface{
    private RealSubject realSubject;

    public ProxySubject(RealSubject realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public void request() {
        Log.i("ProxySubject","ProxySubject request before");
        realSubject.request();
        Log.i("ProxySubject","ProxySubject request after");
    }
}

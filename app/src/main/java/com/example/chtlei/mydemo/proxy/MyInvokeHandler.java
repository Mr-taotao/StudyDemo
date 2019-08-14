package com.example.chtlei.mydemo.proxy;

import android.util.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by chtlei on 19-7-12.
 */

public class MyInvokeHandler implements InvocationHandler{
    private ProxyInterface realSubject;

    public MyInvokeHandler(ProxyInterface realSubject) {
        this.realSubject = realSubject;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Log.i("MyInvokeHandler","MyInvokeHandler invoke before");
        Object result = method.invoke(realSubject, args);
        Log.i("MyInvokeHandler","MyInvokeHandler invoke after");

        return result;
    }
}

package com.example.chtlei.mydemo.reflect;

import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectMethodImpl {

    private static ConcurrentHashMap<String,ReflectMethodItem> mMethodCaches = new ConcurrentHashMap<>();

    private ReflectMethodImpl(){

    }

    private static void addReflectMethod(Object object) {
        try {
            String moduleClassName = object.getClass().getName();
            if (mMethodCaches.get(object.getClass().getName()) != null) {
                return;
            }

            Method[] methods = object.getClass().getMethods();
            for (int i = 0; i < methods.length; i++) {
                ReflectMethodInterface reflectMethodInterfaces = methods[i].getAnnotation(ReflectMethodInterface.class);
                if (null != reflectMethodInterfaces) {
                    String moduleMethodName = methods[i].getName();
                    ReflectMethodItem methodItem = new ReflectMethodItem(moduleClassName, moduleMethodName, object, methods[i]);

                    mMethodCaches.putIfAbsent(moduleClassName + "#" + moduleMethodName, methodItem);
                }
            }

            mMethodCaches.putIfAbsent(moduleClassName, new ReflectMethodItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getInvoke(String reflectMethodClassName, String reflectMethodName, Object params) {
        if (TextUtils.isEmpty(reflectMethodClassName) || TextUtils.isEmpty(reflectMethodName)) {
            return null;
        }

        try {
            ReflectMethodItem reflectMethodItem = mMethodCaches.get(reflectMethodClassName + "#" + reflectMethodName);
            if (null == reflectMethodItem) {
                if (mMethodCaches.get(reflectMethodClassName) != null) {
                    return null;
                }

                Class<?> cls = Class.forName(reflectMethodClassName);
                Object obj = cls.newInstance();
                addReflectMethod(obj);
            }

            Object result =(Object) reflectMethodItem.mMethod.invoke(reflectMethodItem.mObject,params);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

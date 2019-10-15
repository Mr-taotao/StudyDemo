package com.example.chtlei.mydemo.reflect;

import java.lang.reflect.Method;

public class ReflectMethodItem {

    public String reflectModuleClassName;
    public String reflectModuleMethodName;

    public Object mObject;
    public Method mMethod;

    ReflectMethodItem() {

    }

    public ReflectMethodItem (String reflectModuleClassName, String reflectModuleMethodName,
            Object mObject, Method mMethod) {
        this.reflectModuleClassName = reflectModuleClassName;
        this.reflectModuleMethodName = reflectModuleMethodName;
        this.mObject = mObject;
        this.mMethod = mMethod;
    }
}

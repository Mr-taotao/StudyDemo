package com.example.chtlei.mydemo.singleton;

/**
 * Created by chtlei on 18-10-25.
 */

public class Singleton {

    private Singleton () {

    }

    /**
     * 双重校验锁模式实现单例
     */
    private static volatile Singleton mSingleton = null;
    public static Singleton getInstance () {
        if (mSingleton == null) {
            synchronized (Singleton.class) {
                if (mSingleton == null) {
                    mSingleton = new Singleton();
                }
            }
        }
        return mSingleton;
    }

    /**
     * 静态内部类实现单例模式
     */
    private static class SingletonHolder{
        public static Singleton instance = new Singleton();
    }
    public static Singleton newInstance(){
        return SingletonHolder.instance;
    }

    
}

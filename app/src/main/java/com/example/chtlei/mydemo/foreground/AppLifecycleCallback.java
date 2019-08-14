package com.example.chtlei.mydemo.foreground;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import com.example.chtlei.mydemo.MyDemoApplication;

/**
 * Created by chtlei on 18-10-22.
 */

public class AppLifecycleCallback implements Application.ActivityLifecycleCallbacks {
    private static final String TAG = "AppLifecycleCallback";

    private static boolean isForeground = true;
    private int appCount = 0;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        MyDemoApplication.getInstance().addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        appCount++;
        if (!isForeground) {
            isForeground = true;
            Log.i(TAG, "app into forground");
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {
        appCount--;
        if (!isForegroundAppValue()) {
            isForeground = false;
            Log.i(TAG, "app into background ");
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        MyDemoApplication.getInstance().removeActivity(activity);
    }

    private boolean isForegroundAppValue() {
        return appCount > 0;
    }

    public static boolean isAppForeground(){
        return isForeground;
    }
}

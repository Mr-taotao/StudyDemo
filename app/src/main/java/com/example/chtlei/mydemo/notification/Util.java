package com.example.chtlei.mydemo.notification;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by chtlei on 18-10-25.
 */

public class Util {
    /**
     * Check whether the app launched
     *
     * @param context     context
     * @param packageName package name
     * @return true if the app is running.
     */
    public static boolean isAppAlive(Context context, String packageName) {
        ActivityManager activityManager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> processInfos
                = activityManager.getRunningAppProcesses();

        for (int i = 0; i < processInfos.size(); i++) {
            if (processInfos.get(i).processName.equals(packageName)) {
                Log.i("Application Launch",
                        String.format("the %s is running, return true", packageName));
                return true;
            }
        }
        Log.i("Application Launch",
                String.format("the %s is not running, return false", packageName));
        return false;
    }
}

package com.example.chtlei.mydemo.foreground;

import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by chtlei on 18-10-22.
 */

public class ForegroundUtils {
    private static final String TAG = "ForegroundUtils";

    /**
     * 判断app是否处于前台,根据ActivityLifecycleCallbacks回调判断.
     */
    public static boolean isAppForeground(){
        return AppLifecycleCallback.isAppForeground();
    }


    /**
     * 判断app是否处于前台,根据应用包名是否匹配来判断.
     * @param context
     * @return
     */
    public static boolean isAppForeground (Context context) {
        ActivityManager manager = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = manager.getRunningAppProcesses();
        if(appProcessInfos == null || appProcessInfos.isEmpty()){
            return false;
        }

        Log.i(TAG,"appProcessInfos is " + appProcessInfos.toString());

        String packageName = context.getPackageName();
        for(ActivityManager.RunningAppProcessInfo info : appProcessInfos){
            String processName = info.processName;
            Log.i(TAG,"processName is " + processName + " packageName is " + packageName);
            //当前应用处于运行中，并且在前台
            if(processName.equals(packageName) && info.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
//                Log.i(TAG,"isAppForegtound");
                return true;
            }
        }

        return false;
    }

    /**
     * 判断app是否处于前台,5.0以后,getRunningTasks方法被废弃.
     * @param context
     * @return
     */
    public static boolean isRunningForeground (Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true ;
        }
        return false ;
    }
}

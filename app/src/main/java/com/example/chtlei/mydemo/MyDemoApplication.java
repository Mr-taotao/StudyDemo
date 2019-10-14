package com.example.chtlei.mydemo;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.chtlei.mydemo.db.DaoMaster;
import com.example.chtlei.mydemo.db.DaoSession;
import com.example.chtlei.mydemo.foreground.AppLifecycleCallback;
import com.example.chtlei.mydemo.uncatchexception.UnCatchException;
import com.google.android.gms.ads.MobileAds;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by chtlei on 18-10-22.
 */

public class MyDemoApplication extends Application {
    private static final String TAG = "MyDemoApplication";

    private static MyDemoApplication mMyDemoApplication;
    private List<Activity> list = Collections.synchronizedList(new LinkedList<Activity>());
    AppLifecycleCallback appLifecycleCallback;
    DaoSession mDaoSession;

    public static MyDemoApplication getInstance () {

        return mMyDemoApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mMyDemoApplication = this;

        if (appLifecycleCallback == null) {
            appLifecycleCallback = new AppLifecycleCallback();
            this.registerActivityLifecycleCallbacks(appLifecycleCallback);
        }

        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

        UnCatchException.startCatchException();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (appLifecycleCallback != null) {
            this.unregisterActivityLifecycleCallbacks(appLifecycleCallback);
            appLifecycleCallback = null;
        }
    }

    private void initGreenDao () {
        DaoMaster.DevOpenHelper dataHelp = new DaoMaster.DevOpenHelper(this,"dao.db");
        SQLiteDatabase db = dataHelp.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        mDaoSession = daoMaster.newSession();
    }

    /**
     * add the activity
     *
     * @param activity activity
     */
    public void addActivity(Activity activity) {
        if (activity == null)
            return;
        list.add(activity);
    }

    /**
     * remove the activity
     *
     * @param activity activity
     */
    public void removeActivity(Activity activity) {
        if (activity == null)
            return;
        list.remove(activity);
    }

    /**
     * get top activity
     *
     * @return activity
     */
    public Activity topActivity() {
        if (list == null || list.isEmpty())
            return null;
        return list.get(list.size() - 1);
    }

    /**
     * finish the given activity
     *
     * @param activity activity
     */
    public void finishActivity(Activity activity) {
        if (list == null || list.isEmpty())
            return;
        if (activity == null)
            return;
        removeActivity(activity);
        activity.finish();
    }

    /**
     * finish the top activity
     */
    public void finishtopActivity() {
        if (list == null || list.isEmpty())
            return;
        finishActivity(list.get(list.size() - 1));
    }

    /**
     * check if all activity had bean finished
     *
     * @return true or false
     */
    public boolean isAllActivityFinished() {
        return list == null || list.isEmpty();
    }
}

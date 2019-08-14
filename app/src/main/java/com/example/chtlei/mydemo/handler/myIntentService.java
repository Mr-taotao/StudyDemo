package com.example.chtlei.mydemo.handler;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

/**
 * Created by chtlei on 19-6-3.
 */

public class myIntentService extends IntentService {

    //调用父类的构造函数
    //构造函数参数=工作线程的名字
    public myIntentService() {
        super("myIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        stopSelf();
    }
}

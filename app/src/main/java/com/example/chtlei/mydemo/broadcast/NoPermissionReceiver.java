package com.example.chtlei.mydemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.chtlei.mydemo.foreground.ForegroundTestService;

/**
 * Created by chtlei on 18-10-24.
 */

public class NoPermissionReceiver extends BroadcastReceiver {
    private static final String TAG = NoPermissionReceiver.class.getSimpleName();
    public static final String ACTION_FOREGROUND = "com.example.chtlei.mydemo.foreground";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG,"NoPermissionReceiver onReceive action is :" + intent.getAction());
        String action = intent.getAction();
        if (action != null && ACTION_FOREGROUND.equals(intent.getAction())) {
            Toast.makeText(context,"onReceive :" + action,Toast.LENGTH_SHORT).show();
            context.startService(new Intent(context, ForegroundTestService.class));
        }
    }
}

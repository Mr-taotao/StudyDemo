package com.example.chtlei.mydemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by chtlei on 18-10-12.
 */

public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION = "com.example.chtlei.mydemo.broadcast";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("LCT","onReceive action is :" + intent.getAction());
        String action = intent.getAction();
        if (action != null && ACTION.equals(intent.getAction())) {
            Toast.makeText(context,"onReceive :" + action,Toast.LENGTH_SHORT).show();
        }
    }
}

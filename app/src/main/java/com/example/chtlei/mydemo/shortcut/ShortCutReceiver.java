package com.example.chtlei.mydemo.shortcut;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by chtlei on 18-10-8.
 */

public class ShortCutReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("ShortCutReceiver","onReceive action is :" + intent.getAction());
        String msg = intent.getStringExtra("shortcut_name");
        Toast.makeText(context, msg + " is created" ,Toast.LENGTH_LONG).show();
    }
}


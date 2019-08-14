package com.example.chtlei.mydemo.webview;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.example.chtlei.mydemo.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by chtlei on 18-12-7.
 */

public class NightMode {
    public static String getCss(Context context){
        String nightCode = null;
        InputStream is = context.getResources().openRawResource(R.raw.night);
        byte[] buffer = new byte[0];
        try {
            buffer = new byte[is.available()];
            is.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String s = buffer.toString();
        nightCode = Base64.encodeToString(buffer, Base64.NO_WRAP);

        Log.i("NightMode","nightCode is : " + s);
        return nightCode;
    }
}

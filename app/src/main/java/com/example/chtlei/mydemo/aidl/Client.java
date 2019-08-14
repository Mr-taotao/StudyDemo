package com.example.chtlei.mydemo.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.util.List;

/**
 * Created by chtlei on 19-6-5.
 */

public class Client {
    public static Client getInstance() {
        return Holder.INSTANCE;
    }

    private static class Holder {
        public static Client INSTANCE = new Client();
    }


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IBookManager iBookManager = IBookManager.Stub.asInterface(service);

            try {
                List<Book> bookList = iBookManager.getBookList();
                Log.i("LCT","Client query Service data type is " + bookList.getClass().getCanonicalName());
                Log.i("LCT","Client query Service data is " + bookList.get(0).getBookName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    public void bindService(Context context) {
        Intent intent = new Intent(context, BookService.class);
        context.bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void unBindService(Context context) {
        if (null != mConnection) {
            context.unbindService(mConnection);
        }
    }

}

package com.example.chtlei.mydemo.handler;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.example.chtlei.mydemo.R;

/**
 * Created by chtlei on 19-1-30.
 */

public class MyHandlerTest extends Activity {
    private static final String TAG = MyHandlerTest.class.getSimpleName();

    com.example.chtlei.mydemo.handler.Handler ThreadHandler;

    private TextView handlerTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_myhandler);

        handlerTv = findViewById(R.id.handler_tv);
//        handler2.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },1000);
//        Looper.prepare();
//
//        final com.example.chtlei.mydemo.handler.Handler myHandler = new com.example.chtlei.mydemo.handler.Handler() {
//            @Override
//            public void handleMessage(com.example.chtlei.mydemo.handler.Message msg) {
//                super.handleMessage(msg);
//                Log.i(TAG, Thread.currentThread().getName() + " handleMessage " + msg.toString());
//            }
//        };

//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                while (true){
//                    try{
//                        sleep(500);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                    com.example.chtlei.mydemo.handler.Message message = new com.example.chtlei.mydemo.handler.Message(Thread.currentThread().getId());
//                    myHandler.sendMessage(new com.example.chtlei.mydemo.handler.Message(message));
//                    Log.i(TAG,Thread.currentThread().getName() + " sendMessage " + message.toString());
//                }
//            }
//        }.start();

//        for (int i = 0; i < 10; i++) {
//            new Thread() {
//                @Override
//                public void run() {
//                    super.run();
//                    while (true) {
//                        try {
//                            sleep(500);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//                        com.example.chtlei.mydemo.handler.Message message = new com.example.chtlei.mydemo.handler.Message(Thread.currentThread().getId());
//                        myHandler.sendMessage(new com.example.chtlei.mydemo.handler.Message(message));
//                        Log.i(TAG, Thread.currentThread().getName() + " sendMessage " + message.toString());
//                    }
//                }
//            }.start();
//        }

        HandlerThread mHandler = new HandlerThread("handler");

        mHandler.start();

        ThreadHandler = new com.example.chtlei.mydemo.handler.Handler(mHandler.getLooper()){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                Log.i(TAG, Thread.currentThread().getName() + " handleMessage " + msg.toString());
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {

                }
                handlerTv.setText("Finish");
            }
        };


//        Looper.loop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Message message = new Message(Thread.currentThread().getName());
        ThreadHandler.sendMessage(message);
        Log.i(TAG, Thread.currentThread().getName() + " sendMessage " + message.toString());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    //    Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };
//
//    Handler handler2 = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            return false;
//        }
//    }) {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    };
}

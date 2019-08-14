package com.example.chtlei.mydemo.handler;

/**
 * Created by chtlei on 19-5-29.
 */

public class Handler {
    private MessageQueue mQueue;

    public Handler(){
        Looper looper = Looper.myLooper();
        mQueue = looper.mQueue;
    }

    public Handler(Looper looper) {
        Looper looper1 = looper;
        mQueue = looper1.mQueue;
    }

    public void sendMessage(Message msg) {
        msg.target = this;
        mQueue.enqueueMessage(msg);
    }

    public void handleMessage(Message msg) {

    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }
}

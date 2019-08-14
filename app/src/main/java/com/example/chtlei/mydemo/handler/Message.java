package com.example.chtlei.mydemo.handler;

/**
 * Created by chtlei on 19-5-29.
 */

public class Message {
    private static final String TAG = Message.class.getSimpleName();
    Object object;
    Handler target;

    public Message(Object obj){
        this.object = obj;
    }

    @Override
    public String toString() {
        return object.toString();
    }
}

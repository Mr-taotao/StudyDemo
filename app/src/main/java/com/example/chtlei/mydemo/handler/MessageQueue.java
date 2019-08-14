package com.example.chtlei.mydemo.handler;


import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by chtlei on 19-5-29.
 */

public class MessageQueue {

    private static BlockingQueue<Message> mQueue = new ArrayBlockingQueue<Message>(15);

    public Message next() {
        Message msg = null;
        try {
            msg = mQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return msg;
    }

    public void enqueueMessage(Message msg) {
        try {
            mQueue.put(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

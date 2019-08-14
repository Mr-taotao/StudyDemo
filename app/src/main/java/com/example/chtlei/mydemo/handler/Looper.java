package com.example.chtlei.mydemo.handler;

/**
 * Created by chtlei on 19-5-29.
 */

public class Looper {
    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<Looper>();
    final MessageQueue mQueue;

    private Looper() {
        mQueue = new MessageQueue();
    }

    public static void prepare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    public static void loop() {
        final Looper looper = myLooper();
        final MessageQueue mQueue = looper.mQueue;
        for (;;) {
            Message msg = mQueue.next();
            if (msg != null) {
                msg.target.dispatchMessage(msg);
            }
        }
    }


}

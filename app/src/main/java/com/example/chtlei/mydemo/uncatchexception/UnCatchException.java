package com.example.chtlei.mydemo.uncatchexception;

/**
 * Created by chtlei on 18-11-8.
 */

public class UnCatchException {
    public static void startCatchException () {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {

            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                // When exiting crash report dialog, we will also kill process
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(10);
            }
        });
    }
}

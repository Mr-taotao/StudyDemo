package com.example.chtlei.mydemo.singleton;

/**
 * Created by chtlei on 19-7-13.
 */

public class Single {
    private static final Single ourInstance = new Single();

    public static Single getInstance() {
        return ourInstance;
    }

    private Single() {
    }
}

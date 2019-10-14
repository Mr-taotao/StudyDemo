package com.example.chtlei.mydemo.eventbus.event;

public class RxJavaEventMessage {
    private String message;

    public RxJavaEventMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

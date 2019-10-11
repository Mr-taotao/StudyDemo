package com.example.chtlei.mydemo.eventbus.event;

public class BaseEventMessage {
    private String Message;

    public BaseEventMessage(String Message) {
        this.Message = Message;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}

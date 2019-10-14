package com.example.chtlei.mydemo.rxjava;

import java.util.List;

public class RxJavaBean {
    private int code;
    private String msg;
    private List<Message> message;

    public RxJavaBean(int code, String msg, List<Message> message) {
        this.code = code;
        this.msg = msg;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Message> getMessage() {
        return message;
    }

    public void setMessage(List<Message> message) {
        this.message = message;
    }

    public static class Message {
        private String name;
        private int age;

        public Message(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "name is " + name
                    + "age is " + age;
        }
    }
}

package com.example.chtlei.mydemo.deviceinfo;

/**
 * Created by chtlei on 18-10-9.
 */

public class DeviceItem {
    private String title;
    private String info;

    public DeviceItem(String title, String info) {
        this.title = title;
        this.info = info;
    }

    public String getTitle(){
        return title;
    }

    public String getInfo(){
        return info;
    }
}

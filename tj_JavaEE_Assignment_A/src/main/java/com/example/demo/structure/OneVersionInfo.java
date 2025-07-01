package com.example.demo.structure;

public class OneVersionInfo {

    String time;
    String content;

    public OneVersionInfo(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

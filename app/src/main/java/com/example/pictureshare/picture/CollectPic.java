package com.example.pictureshare.picture;

public class CollectPic {
    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private int pid;
    //图片访问地址
    private String url;
}

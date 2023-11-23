package com.example.pictureshare.picture;

public class PictureEntity  {

    //图片id
    private int pid;
    //图片访问地址
    private String url;
    //图片标题
    private String title;
    //上传图片的用户
    private String username;
    //图片的点赞数
    private int like_num;


    private int collect_num;

    private int down_num;


    private boolean is_like;//是否被本机用户点赞

    private boolean is_collect;

    public PictureEntity(){

    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getCollect_num() {
        return collect_num;
    }

    public void setCollect_num(int collect_num) {
        this.collect_num = collect_num;
    }

    public int getDown_num() {
        return down_num;
    }

    public void setDown_num(int down_num) {
        this.down_num = down_num;
    }
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

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isIs_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public boolean isIs_collect() {
        return is_collect;
    }

    public void setIs_collect(boolean is_collect) {
        this.is_collect = is_collect;
    }


}
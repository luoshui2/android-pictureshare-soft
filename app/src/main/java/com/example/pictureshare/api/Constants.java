package com.example.pictureshare.api;

//接口信息类
public final class Constants {
    private Constants() {
    }

    public static String SERVER_URL = "http://8.134.239.34:8080";//自己的服务器IP和端口
    public static String get_name = "/ceshi/get_user?name=";
    public static String get_names = "/ceshi/get_users?name=";
    public static String upload_name = "/ceshi/upload_user?name=";
    public static String password = "&password=";

    public static String upload = "/ceshi/upload";

    public static String get_pic = "/ceshi/get_pic";

    public static String pic_like = "/ceshi/pic_like";//更新图片的点赞
    public static String pic_collect = "/ceshi/pic_collect";//更新图片的收藏

    public static String pic_down = "/ceshi/updatedownload";//更新图片的下载

    public static String pic_collect_url = "/ceshi/get_info";//获取收藏图片的url

}
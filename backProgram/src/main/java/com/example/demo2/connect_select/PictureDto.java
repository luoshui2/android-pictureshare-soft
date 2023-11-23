package com.example.demo2.connect_select;

public class PictureDto {
    private int id;
    private String title;
    private String usename;
    private int likeNumber;
    private int collectNumber;
    private int downloadNumber;
    private String picUrl;

    public PictureDto(int id, String title, String usename, int likeNumber, int collectNumber, int downloadNumber, String picUrl) {
        this.id = id;
        this.title = title;
        this.usename = usename;
        this.likeNumber = likeNumber;
        this.collectNumber = collectNumber;
        this.downloadNumber = downloadNumber;
        this.picUrl = picUrl;
    }
}

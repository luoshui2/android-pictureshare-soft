package com.example.demo2.database_pic_information;

import com.example.demo2.database_picture.Picture;

import javax.persistence.*;

@Entity
@Table(name = "pic_information")
public class Pic_information {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getPic_path() {
        return pic_path;
    }

    public void setPic_path(String pic_path) {
        this.pic_path = pic_path;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    @Id
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "upload_date")
    private String upload_date;
    @Column(name = "pic_path")
    private String pic_path;

    @Column(name = "pic_url")
    private String pic_url;

}

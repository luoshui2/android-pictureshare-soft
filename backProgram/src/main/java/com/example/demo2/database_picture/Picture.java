package com.example.demo2.database_picture;

import com.example.demo2.database_pic_information.Pic_information;

import javax.persistence.*;

//picture实体类
@Entity
@Table(name = "picture")
public class Picture {

    public Picture() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String filename) {
        this.name = filename;
    }


    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public int getLike_number() {
        return like_number;
    }

    public void setLike_number(Integer like_number) {
        this.like_number = like_number;
    }

    public int getDownload_number() {
        return download_number;
    }

    public void setDownload_number(Integer download_number) {
        this.download_number = download_number;
    }

    public int getCollect_number() {
        return collect_number;
    }

    public void setCollect_number(Integer collect_number) {
        this.collect_number = collect_number;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;

    @Column(name = "title")
    private String title;
    @Column(name = "usename ")
    private String usename;


    @Column(name = "like_number ")
    private int like_number;

    @Column(name = "collect_number ")
    private int collect_number;

    @Column(name = "download_number ")
    private int download_number;

    @OneToOne
    @JoinColumn(name = "name", referencedColumnName = "name", insertable = false, updatable = false)//外键约束
    private Pic_information picInformation;

}

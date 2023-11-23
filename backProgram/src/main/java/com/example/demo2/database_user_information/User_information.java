package com.example.demo2.database_user_information;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_information")
public class User_information {
    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public int getLike_total() {
        return like_total;
    }

    public void setLike_total(Integer like_total) {
        this.like_total = like_total;
    }

    public int getCollect_total() {
        return collect_total;
    }

    public void setCollect_total(Integer collect_total) {
        this.collect_total = collect_total;
    }

    @Id
    private String usename;
    @Column(name = "like_total")
    private int like_total;
    @Column(name = "collect_total")
    private int collect_total;
}

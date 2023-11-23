package com.example.demo2.pic_user;

import javax.persistence.*;

@Entity
@Table(name = "pic_user")
public class Pic_user {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Integer relationId;

    @Column(name = "pic_id")
    private Integer picId;


    @Column(name = "user_id")
    private String userId;

    @Column(name = "like_check")
    private boolean likeCheck;

    public Integer getRelationId() {
        return relationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String user) {
        this.userId = user;
    }
    public void setRelationId(Integer relation_id) {
        this.relationId = relation_id;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer pic_id) {
        this.picId = pic_id;
    }



    public boolean isLikeCheck() {
        return likeCheck;
    }

    public void setLikeCheck(boolean like_check) {
        this.likeCheck = like_check;
    }

    public boolean isCollectCheck() {
        return collectCheck;
    }

    public void setCollectCheck(boolean collect_check) {
        this.collectCheck = collect_check;
    }

    @Column(name = "collect_check")
    private boolean collectCheck;

    // 其他属性和方法

    // Getter 和 Setter 方法
}

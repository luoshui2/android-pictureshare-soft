package com.example.demo2.database_picture;


import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;

//用户的数据库操作接口
public interface Picture_interface {
    //进行数据库添加的接口
    Picture add(Picture pic);

    Page<Picture> findAll_page(Pageable pageable);

    Page<Object[]> findPicAndInfo(Pageable pageable);

    boolean updateLikeNumber(Integer id,int number);//更新点赞

    boolean updateCollectNumber(Integer id,int number);//更新收藏

    boolean updateDownNumber(Integer id,int number);//更新下载

    String findurl(int id);

}

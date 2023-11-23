package com.example.demo2.database_picture;


import org.hibernate.annotations.Parameter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//DAO层
@Repository
public interface PictureRepository extends JpaRepository<Picture,Integer> {
    @Override
    Page<Picture> findAll(Pageable pageable);
    @Query("SELECT p.id,p.title,p.usename,p.like_number,p.collect_number,p.download_number,i.pic_path FROM Picture p JOIN p.picInformation i")
    Page<Object[]> findPictureWithPic_information(Pageable pageable);
    @Query("select i.pic_path from Picture p JOIN p.picInformation i where p.id = :picid")
    String findbyid_url(@Param("picid") int id);
}


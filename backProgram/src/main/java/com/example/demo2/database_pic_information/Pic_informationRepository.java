package com.example.demo2.database_pic_information;

import com.example.demo2.database_picture.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Pic_informationRepository extends JpaRepository<Pic_information,String> {
}

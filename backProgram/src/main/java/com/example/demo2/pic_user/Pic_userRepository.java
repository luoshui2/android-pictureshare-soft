package com.example.demo2.pic_user;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Pic_userRepository extends JpaRepository<Pic_user,Integer> {
    List<Pic_user> findByUserId(String name);

    Pic_user findByPicIdAndUserId(Integer id,String username);

    List<Pic_user> findByUserIdAndCollectCheck(String name,boolean check);

}

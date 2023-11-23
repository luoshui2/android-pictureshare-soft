package com.example.demo2.pic_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Pic_userService implements Pic_user_interface{
    @Autowired
    private Pic_userRepository repository;
    @Override
    public Pic_user add(Pic_user p) {
        return repository.save(p);
    }

    @Override
    public List<Pic_user> findby_user(String name) {
        if(repository.findByUserId(name).isEmpty()){
            return null;
        }else {
            return repository.findByUserId(name);
        }
    }

    @Override
    public boolean updateLikeCheck(Integer pic_id, String name,boolean like) {
        Pic_user picUser = repository.findByPicIdAndUserId(pic_id,name);
        if(picUser != null){
            picUser.setLikeCheck(like);
            repository.save(picUser);

        }
        else {
            Pic_user p = new Pic_user();
            p.setPicId(pic_id);
            p.setUserId(name);
            p.setLikeCheck(like);
            repository.save(p);
        }

        return true;
    }

    @Override
    public boolean updateCollectCheck(Integer pic_id, String name, boolean collect) {
        Pic_user picUser = repository.findByPicIdAndUserId(pic_id,name);
        if(picUser != null){
            picUser.setCollectCheck(collect);
            repository.save(picUser);

        }
        else {
            Pic_user p = new Pic_user();
            p.setPicId(pic_id);
            p.setUserId(name);
            p.setCollectCheck(collect);
            repository.save(p);
        }

        return true;
    }

    @Override
    public List<Pic_user> findPicId(String name, boolean check) {
        return repository.findByUserIdAndCollectCheck(name,check);
    }

}

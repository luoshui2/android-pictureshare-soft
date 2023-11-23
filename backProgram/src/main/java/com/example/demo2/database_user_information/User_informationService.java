package com.example.demo2.database_user_information;

import com.example.demo2.database_picture.Picture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class User_informationService implements User_information_interface{
    @Autowired
    private User_informationRepository repository;
    @Override
    public User_information add(User_information u) {
        return repository.save(u);
    }

    //更新liketotal
    @Override
    public boolean update_userinfo_like(String name, boolean flag) {
        Optional<User_information> userInformation = repository.findById(name);
        if(userInformation.isPresent()){
            User_information u = userInformation.get();
            if(u.getLike_total() <= 0){
                if(flag){
                    u.setLike_total(Integer.valueOf(1));
                }else{
                    u.setLike_total(Integer.valueOf(0));
                }
            }else{
                if(flag){
                    u.setLike_total(Integer.valueOf(u.getLike_total() + 1));
                }else{
                    u.setLike_total(Integer.valueOf(u.getLike_total() - 1));
                }
            }
            repository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean update_userinfo_collect(String name, boolean flag) {
        Optional<User_information> userInformation = repository.findById(name);
        if(userInformation.isPresent()){
            User_information u = userInformation.get();
            if(u.getCollect_total() <= 0){
                if(flag){
                    u.setCollect_total(Integer.valueOf(1));
                }else{
                    u.setCollect_total(Integer.valueOf(0));
                }
            }else{
                if(flag){
                    u.setCollect_total(Integer.valueOf(u.getLike_total() + 1));
                }else{
                    u.setCollect_total(Integer.valueOf(u.getLike_total() - 1));
                }
            }
            repository.save(u);
            return true;
        }
        return false;
    }

    @Override
    public User_information getinfo(String name) {
        Optional<User_information> userInformation = repository.findById(name);
        if(userInformation.isPresent()){
            User_information u = userInformation.get();
            return u;
        }
        return null;
    }
}

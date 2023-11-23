package com.example.demo2.database_user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//实现用户进行数据库操作接口
@Service
public class UserService implements User_interface{
    @Autowired
    private UserRepository userRepository;

    @Override
    public User add(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findById(String username) {
        if(userRepository.findById(username).isPresent()){
            return userRepository.findById(username).get();
        }else{
            return null;
        }
    }
}

package com.example.demo2.database_user_information;

public interface User_information_interface {

    User_information add(User_information u);

    boolean update_userinfo_like(String name, boolean flag);//更新喜欢

    boolean update_userinfo_collect(String name, boolean flag);//更新喜欢

    User_information getinfo(String name);
}

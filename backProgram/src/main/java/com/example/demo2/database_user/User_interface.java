package com.example.demo2.database_user;



//用户的数据库操作接口
public interface User_interface {
    //进行数据库添加的接口
    public User add(User user);
    //通过用户名查询用户的信息
    public User findById(String username);


}

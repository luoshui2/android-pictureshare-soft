package com.example.pictureshare.users;

import java.io.Serializable;

//用户账号密码类
public class User implements Serializable {
    public String getUsename() {
        return usename;
    }

    public void setUsename(String usename) {
        this.usename = usename;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    private String usename;

    private String password;
}

package com.example.tacademy.retrofithttpssslpemtest;

/**
 * Created by Tacademy on 2017-02-20.
 */

public class ReqJoin {
    String user_id;
    String user_name;
    String user_email;
    String password;

    public ReqJoin() {
    }

    public ReqJoin(String user_id, String user_name, String user_email, String password) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.password = password;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

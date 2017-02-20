package com.example.tacademy.retrofithttpssslpemtest;

/**
 * Created by Tacademy on 2017-02-20.
 */

public class ResModel {
    int code;
    String message;

    public ResModel() {
    }

    public ResModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.androidlover5852.fetcher.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginModel implements Serializable {
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("password")
    private String password;

    public LoginModel() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginModel(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

}

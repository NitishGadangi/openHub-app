package com.androidlover5852.fetcher.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserDataModel  implements Serializable {
    @SerializedName("name")
    private String name;
    @SerializedName("customer_id")
    private String customer_id;
    @SerializedName("email")
    private String email;
    @SerializedName("mobile")
    private String mobile;
    @SerializedName("result")
    private Integer result;

    public UserDataModel(){}

    public UserDataModel(String name, String customer_id, String email, String mobile)
    {
        this.name=name;
        this.customer_id=customer_id;
        this.email=email;
        this.mobile=mobile;
    }

    public String getName() {
        return name;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public Integer getResult() {
        return result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}

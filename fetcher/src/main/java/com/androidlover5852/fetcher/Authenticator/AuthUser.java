package com.androidlover5852.fetcher.Authenticator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import com.androidlover5852.fetcher.Model.UserDataModel;
import com.google.gson.Gson;

public class AuthUser {
    private Activity context;
    private UserDataModel currentUser;
    public AuthUser(Activity context)
    {
        this.context=context;
        getCurrentUser();
    }
    public UserDataModel getCurrentUser() {
        if (getValue("userData")!=null)
        {
            currentUser=new Gson().fromJson(getValue("userData"),UserDataModel.class);
            return currentUser;
        }
        else {
            System.err.println("User not logged in");
            return null;
        }
    }

    public String getName() {

        return getCurrentUser().getName();
    }

    public String getCustomer_id() {

        return getCurrentUser().getCustomer_id();
    }

    public String getEmail() {
        return getCurrentUser().getEmail();
    }

    public String getMobile() {
        return getCurrentUser().getMobile();
    }

    public Integer getResult() {
        return getCurrentUser().getResult();
    }
    private String getValue(String s) {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(s,null);
    }

    public void setName(String name) {
        if (getValue("userData")!=null)
        {
            UserDataModel userDataModel=new Gson().fromJson(getValue("userData"),UserDataModel.class);
            userDataModel.setName(name);
            String gson=new Gson().toJson(userDataModel);
            setValue("userData",gson);
            currentUser=new Gson().fromJson(getValue("userData"),UserDataModel.class);
        }

    }

    private void removeValue(String s)
    {
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.remove(s);
        editor.apply();
    }
    public void setValue(String s, String val) {
        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(s,val);
        editor.apply();
    }


    public void logout()
    {
        if (getCurrentUser()!=null)
        {
            removeValue("userData");

        }
    }

    public boolean isLoggedIn(){

        if (getCurrentUser()!=null)
            return true;
        else return false;
    }
}

package com.androidlover5852.fetcher.Listener;


import com.androidlover5852.fetcher.Model.UserDataModel;

public interface Listener {
    void Listener(boolean success, UserDataModel userDataModel,String body);

}

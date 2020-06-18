package com.androidlover5852.fetcher.Fetcher;

import com.rw.velocity.Velocity;

import java.util.HashMap;
import java.util.LinkedHashSet;

public class Fetcher {
    private static Fetcher instance;
    private static HashMap<String,String> token;
    private static LinkedHashSet<String> certs;
    private Fetcher(){}

    public static Fetcher getInstance(){
        if (instance==null)
            instance=new Fetcher();
        return instance;
    }

    public void setToken(HashMap<String, String> token) {
        this.token = token;
    }

    public void setCerts(LinkedHashSet<String> certs) {
        Fetcher.certs = certs;
    }

    public void initialize(int queue)
    {
        Velocity.initialize(queue);
    }

    public static RequestConverter ref(String url){
        RequestConverter requestConverter=new RequestConverter();
        if (token!=null)
        {
            requestConverter.setToken(token);
        }
        if (certs!=null)
            requestConverter.setCerts(certs);
        return requestConverter.requestUrl(url);
    }
    public static JsonConverter convert(String json){
        return new JsonConverter(json);
    }

}

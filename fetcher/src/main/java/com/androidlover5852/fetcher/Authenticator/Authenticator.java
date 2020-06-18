package com.androidlover5852.fetcher.Authenticator;

import android.app.Activity;
import android.util.Log;

public class Authenticator {
    private static String TAG="Authenticator";
    private static Activity context;
    private Authenticator()
    {}

    public static void initialize(Activity context){
        Authenticator.context=context;
    }

    public static AuthBuilder setUrl(String url){
        return new AuthBuilder(url,context);
    }

    public static AuthUser getInstance(){
        if (context!=null)
            return new AuthUser(context);
        else {
            Log.e(TAG,"initialize Authenticator with context");
            return null;
        }
    }
}

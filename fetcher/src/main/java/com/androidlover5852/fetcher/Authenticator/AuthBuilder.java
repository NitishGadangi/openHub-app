package com.androidlover5852.fetcher.Authenticator;

import android.app.Activity;
import android.content.SharedPreferences;

import com.androidlover5852.fetcher.Fetcher.Fetcher;
import com.androidlover5852.fetcher.Fetcher.JsonConverter;
import com.androidlover5852.fetcher.Listener.Listener;
import com.androidlover5852.fetcher.Listener.OtpListener;
import com.androidlover5852.fetcher.Model.LoginModel;
import com.androidlover5852.fetcher.Model.RegistrationModel;
import com.androidlover5852.fetcher.Model.Response;
import com.androidlover5852.fetcher.Model.UserDataModel;
import com.androidlover5852.fetcher.Fetcher.RequestConverter;
import com.androidlover5852.fetcher.enums.Method;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import androidx.preference.PreferenceManager;

import java.io.Serializable;

public class AuthBuilder{


    private String url;
    private Result result;
    private Listener listener;
    Activity context;
    private SharedPreferences.Editor sharedPreferences;
    private RegistrationModel registrationModel;
    private LoginModel loginModel;
    private boolean debug;

    public AuthBuilder(String url, Activity context)
    {
        result=new Result();
        this.url=url;
        this.context=context;
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context).edit();
    }

    public AuthBuilder setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public AuthBuilder setRegistrationModel(RegistrationModel registrationModel) {
        this.registrationModel = registrationModel;
        return this;
    }

    public AuthBuilder setLoginModel(LoginModel loginModel) {
        this.loginModel = loginModel;
        return this;
    }

    public void sendOtp(String mobileNumber, final OtpListener otpListener)
    {
        Otp otp=new Otp();
        otp.setMobile(mobileNumber);
        Fetcher.ref(url).setDebug(debug).setMethod(Method.POST).setPost(otp)
                .setId("result").connect(Otp.class, new RequestConverter.ResponseListener<Otp>() {
            @Override
            public void onVelocityResponse(Response<Otp> response) {
             if (response.getInteger()==1)
             {

                 otpListener.optListener(
                         Fetcher
                                 .convert(response
                                         .getBody())
                                 .setID("data")
                                 .toObject(Otp.class)
                                 .getObject()
                                 .getOtp()
                 );
             }
             else {
                 otpListener.optListener(0);
             }
            }
        });

    }

    private class Otp implements Serializable {
        @SerializedName("mobile")
        String mobile;
        @SerializedName("otp")
        private int otp;

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getOtp() {
            return otp;
        }

        public void setOtp(int otp) {
            this.otp = otp;
        }
    }
    public void login(LoginModel creds, Listener successListener)
    {
        if (loginModel!=null)
            creds=loginModel;
        this.listener=successListener;
        Fetcher
                .ref(url)
                .setMethod(Method.POST)
                .setPost(creds)
                .setId("result")
                .setDebug(debug)
                .connect(UserDataModel.class, new RequestConverter.ResponseListener<UserDataModel>() {
                    @Override
                    public void onVelocityResponse(Response<UserDataModel> response) {
                        result.setResponse(response.getBody());
                        if (debug)
                        System.out.println("Auth >> "+response.getBody());
                        if (response.getInteger()==1)
                        {
                            UserDataModel userDataModel=Fetcher.convert(response.getBody()).setID("data").setDebug(debug).toObject(UserDataModel.class).getObject();
                            String gson=new Gson().toJson(response.getObject());
                            setValue("userData",gson);
                            if (debug)
                                System.out.println("Saved >> "+gson);
                            result.setUserDataModel(userDataModel);
                            result.setSuccess(true);
                            listener.Listener(result.isSuccess(),result.getUserDataModel(),response.getBody());

                        }

                        else {
                            result.setSuccess(false);
                            listener.Listener(result.isSuccess(),result.getUserDataModel(),response.getBody());
                        }

                    }
                });
    }

    private RegistrationModel tmpModel;

    private UserDataModel userDataModel=new UserDataModel();
    public void register(RegistrationModel model, Listener successListener)
    {
        tmpModel=model;
        if (registrationModel!=null)
            tmpModel=registrationModel;

        this.listener=successListener;
        Fetcher
                .ref(url)
                .setMethod(Method.POST)
                .setPost(tmpModel)
                .setDebug(debug)
                .setId("result")
                .connect(UserDataModel.class, response -> {
                    if (response.isSuccess())
                    {
                        userDataModel.setEmail(tmpModel.getEmail());
                        userDataModel.setMobile(tmpModel.getMobile());
                        userDataModel.setName(tmpModel.getName());

                        if (response.getInteger()==1)
                        {
                            UserDataModel userDataModel=Fetcher
                                    .convert(response.getBody())
                                    .setID("data")
                                    .setDebug(true)
                                    .toObject(UserDataModel.class)
                                    .getObject();

                            userDataModel.setCustomer_id(String.valueOf(userDataModel.getCustomer_id()));
                            String gson=new Gson().toJson(userDataModel);
                            setValue("userData",gson);
                            if (debug)
                                System.out.println("Saved >> "+gson);
                            result.setUserDataModel(userDataModel);
                            result.setSuccess(true);
                            listener.Listener(result.isSuccess(),result.getUserDataModel(),response.getBody());

                        }
                        }
                        else {
                            result.setSuccess(false);
                            listener.Listener(result.isSuccess(),result.getUserDataModel(),response.getBody());
                        }
                    });

    }

    public void setValue(String s, String val) {
        sharedPreferences.putString(s,val);
        sharedPreferences.apply();
    }
    public class Result{
        private String response;
        private UserDataModel userDataModel;
        private boolean success;

        private void setResponse(String response) {
            this.response = response;
        }

        public String getResponse() {
            return response;
        }

        private void setUserDataModel(UserDataModel userDataModel) {
            this.userDataModel = userDataModel;
        }

        public UserDataModel getUserDataModel() {
            return userDataModel;
        }

        private void setSuccess(boolean success) {
            this.success = success;
        }

        public boolean isSuccess() {
            return success;
        }
    }

}
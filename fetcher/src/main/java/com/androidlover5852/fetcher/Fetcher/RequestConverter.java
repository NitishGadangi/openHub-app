package com.androidlover5852.fetcher.Fetcher;

import android.text.TextUtils;

import com.androidlover5852.fetcher.Model.Response;
import com.androidlover5852.fetcher.enums.Method;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rw.velocity.RequestBuilder;
import com.rw.velocity.Velocity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.ListIterator;
import java.util.Map;

import io.reactivex.Observable;

public class RequestConverter {
    private String url;
    private Method method;
    private String id;
    private ResponseListener callback;
    private Object postModel;
    private boolean debug;
    private HashMap<String,String> hashMap;
    private Object object;
    private Gson gson=new Gson();
    private HashMap<String,String> headers;
    private HashMap<String,String> token;
    private LinkedHashSet<String> certs;

    public RequestConverter requestUrl(String url){
        this.url=url;
        return this;
    }
    public RequestConverter setId(String id) {
        this.id = id;
        return this;
    }

    public RequestConverter setMethod(Method method) {
        this.method=method;
        return this;
    }

    public RequestConverter setPost(Object postModel)
    {
        this.postModel=postModel;
        return this;
    }

    public RequestConverter setPost(HashMap<String,String> hashMap)
    {
        this.hashMap=hashMap;
        return this;
    }


    public RequestConverter setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    public <T> RequestConverter withJson(Object obj){
        object=obj;
        return this;
    }

    public RequestConverter setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
        return this;
    }

    public void setToken(HashMap<String, String> token) {
        this.token = token;
    }

    public void setCerts(LinkedHashSet<String> certs) {
        this.certs = certs;
    }

    private JSONArray jsonArray;
    private HashMap<String,String> stringObjectHashMap=new HashMap<>();
    private JSONObject jsonObject;
    public <T> RequestConverter connect(Class<T> t, final ResponseListener<T> callback ){
        this.callback=callback;
        if (postModel!=null)
        {
            stringObjectHashMap=postHash();
        }
        else if (hashMap!=null)
        {
            stringObjectHashMap=hashMap;
        }

        if (token!=null){
            if (headers==null)
                headers=new HashMap<>();
            for (Map.Entry<String, String> stringStringEntry : token.entrySet()) {
                String key=stringStringEntry.getKey();
                String value=stringStringEntry.getValue();
                headers.put(key,value);
            }
        }
        RequestBuilder requestBuilder=new RequestBuilder(url, Velocity.RequestType.Text);
        if (method==null)
            System.err.println("Define request method ex POST,GET,PUT,DELETE");
        else requestBuilder.withRequestMethod(method.name());

        if (object!=null)
            requestBuilder.withJsonBody(object);
        if (postModel!=null)
            requestBuilder
                    .withBody(stringObjectHashMap);
        if (headers!=null)
            requestBuilder.withHeaders(headers);
        if (certs!=null)
            requestBuilder.setCerts(certs);

        requestBuilder
                .connect(output -> {
                    Response<T> response=new Response();
                    if (debug)
                    {
                        System.out.println("DEBUG >> "+output.requestUrl);
                        System.out.println("DEBUG >> "+output.body);
                        System.out.println("DEBUG >> Success="+output.isSuccess);
                    }
                    response.setBody(output.body);
                    response.setRequestHeaders(output.requestHeaders);
                    response.setResponseHeaders(output.responseHeaders);
                    response.setFullUrl(output.fullUrl);
                    response.setRequestUrl(output.requestUrl);
                    response.setResponseCode(output.responseCode);
                    if (output.isSuccess && !TextUtils.isEmpty(output.body))
                    {
                        response=getResponse(response,t);
                    }
                    else {
                        response.setSuccess(false);
                    }
                    RequestConverter.this.callback.onVelocityResponse(response);
                });

        return this;
    }


    public Response getResponse(Response response,Class t){

        try {

            String data;
            if (id==null)
            {
                data=response.getBody();
            }
            else {
                jsonObject=new JSONObject(response.getBody());
                data=jsonObject.get(id).toString();
            }
            Object json =new JSONTokener(data).nextValue();

            if (json instanceof JSONArray)
            {
                if (debug)
                    System.out.println("DEBUG >> JSONArray >> "+data);
                jsonArray=(JSONArray) json;
                ArrayList arrayList=new ArrayList<>();

                for (int i=0;i<jsonArray.length();i++)
                {
                    arrayList.add(gson.fromJson(jsonArray.get(i).toString(),t));
                }
                response.setArrayList(arrayList);

            }
            else if (json instanceof JSONObject)
            {
                if (debug)
                    System.out.println("DEBUG >> JSONObject >> "+data);
                jsonObject=(JSONObject) json;
                response.setObject(gson.fromJson(jsonObject.toString(),t));
            }
            else if (json instanceof String)
            {
                if (debug)
                    System.out.println("DEBUG >> String >> "+data);
                response.setString(data);
            }
            else if (json instanceof Integer)
            {
                if (debug)
                    System.out.println("DEBUG >> Integer >> "+data);
                response.setInteger(Integer.parseInt(json.toString()));
            }
            response.setSuccess(true);
        } catch (JSONException e) {
            e.printStackTrace();
            response.setSuccess(false);
        }
        return response;
    }

    private LinkedHashMap<String,String> postHash(){
        LinkedHashMap<String,String> stringObjectHashMap=new LinkedHashMap<>();
        try {
            JSONObject jobj = new JSONObject(new Gson().toJson(postModel));
            if (debug)
                System.out.println("Debug >> "+jobj);
            Iterator<String> iterator=jobj.keys();

            while (iterator.hasNext())
            {
                String key=iterator.next();
                String val=jobj.getString(key);
                if (val!=null)
                    stringObjectHashMap.put(key,val);
            }
            return stringObjectHashMap;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

    private <T> T getTInstance(Class<T> t) {
        try {
            return t.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        }
    }
    public interface ResponseListener<T>
    {
        void onVelocityResponse(Response<T> response);
    }
}

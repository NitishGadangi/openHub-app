package com.androidlover5852.fetcher.Fetcher;

import com.androidlover5852.fetcher.Model.Response;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;

public class JsonConverter {
    private String json;
    private String id;
    private Response response;
    private boolean debug;

    public JsonConverter(String json)
    {
        response=new Response();
        this.json=json;
    }

    public JsonConverter setID(String id){
        this.id=id;
        return this;
    }

    public JsonConverter setDebug(boolean debug) {
        this.debug = debug;
        return this;
    }

    private JSONObject jsonObject;
    private JSONArray jsonArray;
    private Gson gson=new Gson();
    public <T> Response<T> toObject(Class<T> tClass)
    {
        response.setBody(json);
        System.out.println("sdsa "+json);
        try {
            jsonObject=new JSONObject(json);
            String data;
            if (id==null)
            {
                data=jsonObject.toString();
            }
            else {
                data=jsonObject.get(id).toString();
            }
            Object json =new JSONTokener(data).nextValue();
            if (debug)
            System.out.println("JSON DEBUG >> "+json);
            if (json instanceof JSONArray)
            {
                if (debug)
                    System.out.println("DEBUG >> Json Array >> "+data);

                jsonArray=(JSONArray) json;
                ArrayList<T> arrayList=new ArrayList<>();

                for (int i=0;i<jsonArray.length();i++)
                {
                    arrayList.add(gson.fromJson(jsonArray.get(i).toString(),tClass));
                }
                response.setArrayList(arrayList);

            }
            else if (json instanceof JSONObject)
            {
                if (debug)
                    System.out.println("DEBUG >> Json Object >> "+data);
                jsonObject=(JSONObject) json;
                response.setObject(gson.fromJson(jsonObject.toString(),tClass));
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
            return response;
        } catch (JSONException e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response;
        }
    }

}

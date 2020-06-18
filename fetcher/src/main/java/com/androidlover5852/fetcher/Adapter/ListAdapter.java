package com.androidlover5852.fetcher.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.androidlover5852.fetcher.Model.OptionsBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class ListAdapter<T> extends BaseAdapter {

    private List<T> arrayList;
    private Context activity;
    private int Layout;
    private int MAX_ITEM;

    public void setArrayList(List<T> arrayList) {
        this.arrayList = arrayList;
        this.notifyDataSetChanged();

    }

    public List<T> getArrayList() {
        return arrayList;
    }

    public ListAdapter(Context context,@LayoutRes int layout)
    {
        this.activity=context;
        this.Layout=layout;
    }

    public ListAdapter(Context context,@LayoutRes int layout,int MAX_ITEM)
    {
        this.activity=context;
        this.Layout=layout;
        this.MAX_ITEM=MAX_ITEM;
    }


    public ListAdapter(OptionsBuilder optionsBuilder)
    {
        this.activity=optionsBuilder.getActivity();
        this.Layout=optionsBuilder.getLayout();
        this.MAX_ITEM=optionsBuilder.getMaxItem();
    }

    @Override
    public int getCount() {
        if (arrayList!=null)
            if (MAX_ITEM==0 || MAX_ITEM >=getArrayList().size())
                return getArrayList().size();
            else return MAX_ITEM;
        else return 0;
    }

    @Override
    public Object getItem(int i) {
        return getArrayList().get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v= LayoutInflater.from(activity).inflate(Layout,viewGroup,false);
        getView(i,getArrayList().get(i),v,viewGroup);
        return v;
    }

    public abstract void getView(int i, @NonNull T model, View v,ViewGroup viewGroup);
}

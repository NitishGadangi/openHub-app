package com.androidlover5852.fetcher.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlover5852.fetcher.Holder.BaseViewHolder;
import com.androidlover5852.fetcher.Model.OptionsBuilder;
import com.androidlover5852.fetcher.RecyclerView.EmptyRecyclerView;
import com.androidlover5852.fetcher.Utils.Utils;
import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.androidlover5852.fetcher.Utils.Utils.dpToPx;

public abstract class RecyclerBuilder<T> extends EmptyRecyclerView.Adapter<BaseViewHolder> {

    private List<T> list;
    private int MAX_ITEM;
    private Context context;
    private Activity activity;
    private int Layout;
    private int counter=0;
    private View view;
    private HashMap<String,String> customData;
    public void setList(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addCustomData(String key,String value) {
        if (customData==null)
            customData=new HashMap<>();
        customData.put(key,value);
    }

    protected String getCustomData(String key) {
        if (customData!=null)
        return customData.get(key);
        else return null;
    }

    protected void setLayoutPrams(View view,int height,int weight){
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (height>0){
            layoutParams.height=dpToPx(height);
        }
        else {
            layoutParams.height=height;
        }
        if (weight>0){
            layoutParams.width = dpToPx(weight);
        }else layoutParams.width=weight;

        view.setLayoutParams(layoutParams);
    }

    protected Drawable shimmer(){
        return Utils.shimmer();
    }

    public void addItems(List<T> list){
        if (list!=null)
        {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public RecyclerBuilder(Context context,@LayoutRes int layout)
    {
        this.context=context;
        this.Layout=layout;
    }


    public RecyclerBuilder(Activity activity, @LayoutRes int layout)
    {
        this.activity=activity;
        this.Layout=layout;
        this.context=activity;
    }

    public RecyclerBuilder(Context context,@LayoutRes int layout,int MAX_ITEM)
    {
        this.context=context;
        this.Layout=layout;
        this.MAX_ITEM=MAX_ITEM;
    }

    public Activity getActivity() {
        return activity;
    }

    public RecyclerBuilder(OptionsBuilder optionsBuilder)
    {
        this.context=optionsBuilder.getActivity();
        this.Layout=optionsBuilder.getLayout();
        this.MAX_ITEM=optionsBuilder.getMaxItem();
    }

    public List<T> getArrayList() {
        return list;
    }

    public Context getContext()
    {
        if (getActivity()!=null)
            return getActivity();
        else return context;
    }

    @Override
     public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        counter++;
        view=holder.getView();
        view.setOnClickListener(v -> {
            onClick(v,getItem(position),v.getId());
        });
        onBindViewHolder(holder,position,getItem(position),view);
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(Layout,parent,false);
        return new BaseViewHolder(view);
    }

    public T getItem(int position)
    {
        return list.get(position);
    }

    public abstract void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull T model,View v);

    public  <T> T findViewById(@IdRes int id)
    {
        return (T)view.findViewById(id);
    }
    public boolean isLast(){
        if (getArrayList().size()==counter)
        {
            return true;
        }
        else return false;
    }

    @Override
    public int getItemCount() {
        if (list!=null)
        if (MAX_ITEM==0 || MAX_ITEM >=list.size())
            return list.size();
        else return MAX_ITEM;
        else return 0;
    }

    public void start(Class cls)
    {
        context.startActivity(new Intent(context,cls));
    }

    public void start(Class c, Bundle bundle)
    {
        context.startActivity(new Intent(context,c).putExtras(bundle));
    }

    public void start(Class c, Bundle bundle,int result)
    {
        if (activity!=null)
        activity.startActivityForResult(new Intent(context,c).putExtras(bundle),result);
        else System.out.println("Pass activity instead of context to use this function");
    }


    public void start(Class c, Bundle bundle,boolean finish)
    {
        context.startActivity(new Intent(context,c).putExtras(bundle));
        if (finish)
            getActivity().finish();
    }

    public abstract void onClick(View view,T model, int id);
}

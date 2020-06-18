package com.androidlover5852.fetcher.Utils;

import android.content.Context;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LayoutManager {
    private Context context;
    public LayoutManager(Context context)
    {
        this.context=context;
    }

    public LinearLayoutManager linearLayoutManager(@RecyclerView.Orientation int ORI)
    {
        LinearLayoutManager layoutManager=new LinearLayoutManager(context);
        layoutManager.setOrientation(ORI);
        return layoutManager;
    }

    public RecyclerView.LayoutManager grid(int MAX_ITEM)
    {
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,MAX_ITEM);
        return gridLayoutManager;
    }

}

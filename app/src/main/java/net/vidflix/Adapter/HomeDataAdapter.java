package net.vidflix.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlover5852.fetcher.Adapter.RecyclerBuilder;
import com.androidlover5852.fetcher.Holder.BaseViewHolder;
import com.androidlover5852.fetcher.Utils.Utils;

import net.vidflix.Activity.MoviesActivity;
import net.vidflix.Model.Home.ContentItem;
import net.vidflix.Model.Home.DataItem;
import net.vidflix.R;

import java.util.ArrayList;
import java.util.List;

public class HomeDataAdapter extends RecyclerBuilder<DataItem> {

    public HomeDataAdapter(Context context, int layout) {
        super(context, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull DataItem model, View v) {
        TextView tvTitle=findViewById(R.id.tv_title);
        RecyclerView recyclerView=findViewById(R.id.rv_category);
        HomeCategoryAdapter adapter=new HomeCategoryAdapter(getContext(),R.layout.holder_home_category);
        tvTitle.setText(model.getName());
        recyclerView.setLayoutManager(Utils.linear(RecyclerView.HORIZONTAL));
        recyclerView.setAdapter(adapter);

        List<ContentItem> list=new ArrayList<>();
        for (ContentItem contentItem : model.getContent()) {
            contentItem.setCategoryType(model.getName());
            list.add(contentItem);
        }
        adapter.setList(list);
    }

    @Override
    public void onClick(View view, DataItem model, int id) {
    }
}

package net.vidflix.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.androidlover5852.fetcher.Adapter.RecyclerBuilder;
import com.androidlover5852.fetcher.Fetcher.Fetcher;
import com.androidlover5852.fetcher.Holder.BaseViewHolder;
import com.androidlover5852.fetcher.enums.Method;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import net.vidflix.Activity.MoviesDetailsActivity;
import net.vidflix.Activity.SeriesActivity;
import net.vidflix.Model.MoviePlayback.MoviePlayBackModel;
import net.vidflix.Model.Series.SeriesModel;
import net.vidflix.Model.Voot.ContentItem;
import net.vidflix.R;

import static com.androidlover5852.fetcher.Utils.Utils.dpToPx;

public class VootCategoryAdapter extends RecyclerBuilder<ContentItem> {
    public VootCategoryAdapter(Context context, int layout) {
        super(context, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull ContentItem model, View v) {
        TextView tvCategory=findViewById(R.id.tv_title);
        ImageView imgThumbnail=findViewById(R.id.img_thumbnail);
        MaterialCardView cardView=findViewById(R.id.card_thumb);
        LinearLayout lvData=findViewById(R.id.lv_data);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10,20, 10, 20);
        lvData.setLayoutParams(layoutParams);

        setLayoutPrams(lvData, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutPrams(cardView,160,140);
        cardView.setRadius(dpToPx(10));

        int MAX_TEXT=16;
        int MAX_LENGTH=model.getName().length()>MAX_TEXT?MAX_TEXT:model.getName().length();
        tvCategory.setText(model.getName().substring(0,MAX_LENGTH));

        if (model.getPosterUrl()!=null){
            Glide.with(getContext()).load(model.getPosterUrl()).placeholder(shimmer()).into(imgThumbnail);
        }

    }

    @Override
    public void onClick(View view, ContentItem model, int id) {
        Bundle bundle=new Bundle();
        bundle.putString("ApiUrl",model.getApiUrl());
        if (model.getIsSeries().equals("0") || model.getIsSeries().equals("4")){
            Fetcher.ref(model.getApiUrl()).setMethod(Method.GET)
                    .connect(MoviePlayBackModel.class, response -> {
                        view.setEnabled(true);
                        if (response.getObject()!=null)
                        {
                            bundle.putSerializable("MoviesPlaybackData",response.getObject());
                            start(MoviesDetailsActivity.class,bundle);
                        }
                        else Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_SHORT).show();
                    });
        }
        else if (model.getIsSeries().equals("1")){

            Fetcher.ref(model.getApiUrl()).setMethod(Method.GET).connect(SeriesModel.class, response -> {
                view.setEnabled(true);
                SeriesModel seriesModel=response.getObject();
                bundle.putSerializable("SeriesModel",seriesModel);
                start(SeriesActivity.class,bundle);
            });
        }
    }
}
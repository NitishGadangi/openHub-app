package net.vidflix.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;

import com.androidlover5852.fetcher.Adapter.RecyclerBuilder;
import com.androidlover5852.fetcher.Holder.BaseViewHolder;
import com.bumptech.glide.Glide;
import com.google.android.material.card.MaterialCardView;

import net.vidflix.Activity.MoviesActivity;
import net.vidflix.Activity.VootListingActivity;
import net.vidflix.Model.Home.ContentItem;
import net.vidflix.R;

import static com.androidlover5852.fetcher.Utils.Utils.capitalizeFirstLetter;
import static com.androidlover5852.fetcher.Utils.Utils.dpToPx;

public class HomeCategoryAdapter extends RecyclerBuilder<ContentItem> {
    public HomeCategoryAdapter(Context context, int layout) {
        super(context, layout);
    }
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull ContentItem model, View v) {
        TextView tvCategory=findViewById(R.id.tv_title);
        ImageView imgThumbnail=findViewById(R.id.img_thumbnail);
        MaterialCardView cardView=findViewById(R.id.card_thumb);

        tvCategory.setText(capitalizeFirstLetter(model.getCategoryName()));
        if (model.getThumbnail()!=null){
            Glide.with(getContext()).load(model.getThumbnail()).placeholder(shimmer()).into(imgThumbnail);
        }
        Animation slide = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_left_long);
        tvCategory.startAnimation(slide);

        if (model.getCategoryType().equals("Live TV"))
        {
            LinearLayout linearLayout=findViewById(R.id.lv_data);
            if (linearLayout!=null){
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(25,20, 25, 20);
                linearLayout.setLayoutParams(layoutParams);
            }

            cardView.setPreventCornerOverlap(false);
            cardView.setRadius(100);
            setLayoutPrams(cardView,80,80);
            cardView.setStrokeColor(Color.WHITE);
            cardView.setStrokeWidth(dpToPx(3));
        }
    }

    @Override
    public void onClick(View view, ContentItem model, int id) {
        String cat=model.getType();
        Bundle bundle=new Bundle();
        bundle.putString("ApiUrl",model.getApiUrl());
        bundle.putString("Title",model.getCategoryName());
        if (cat.equals("list"))
        start(MoviesActivity.class,bundle);
        else if (cat.equals("cats"))
            start(VootListingActivity.class,bundle);

    }
}
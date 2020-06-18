package net.vidflix.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.androidlover5852.fetcher.Adapter.RecyclerBuilder;
import com.androidlover5852.fetcher.Holder.BaseViewHolder;
import com.bumptech.glide.Glide;

import net.vidflix.Model.Home.SliderItem;
import net.vidflix.R;

public class SliderAdapter extends RecyclerBuilder<SliderItem> {
    public SliderAdapter(Context context, int layout) {
        super(context, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull SliderItem model, View v) {
        ImageView imageView=findViewById(R.id.img_slide);
        Glide.with(getContext()).load(model.getImg()).placeholder(shimmer()).into(imageView);
    }

    @Override
    public void onClick(View view, SliderItem model, int id) {

    }
}

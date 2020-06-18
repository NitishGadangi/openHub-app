package net.vidflix.Adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.androidlover5852.fetcher.Adapter.RecyclerBuilder;
import com.androidlover5852.fetcher.Holder.BaseViewHolder;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import net.vidflix.R;

public class ShimmerNormalAdapter extends RecyclerBuilder {
    public ShimmerNormalAdapter(Context context, int layout) {
        super(context, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull Object model, View v) {
        ShimmerFrameLayout shimmerFrameLayout=v.findViewById(R.id.live_fb);
    }

    public void cleanup(){
        getArrayList().clear();
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View view, Object model, int id) {

    }

}

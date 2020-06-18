package com.androidlover5852.fetcher.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.Shimmer;
import com.facebook.shimmer.ShimmerDrawable;

public class Utils {
    private static Context context;
    private Utils(){}
    public static void initialize(Context c){
        context=c;
    }

    public static LinearLayoutManager linear(@RecyclerView.Orientation int ORI){
        return new LayoutManager(context).linearLayoutManager(ORI);
    }

    public static RecyclerView.LayoutManager gridLayoutManager(int MAX_ITEM){
        return new LayoutManager(context).grid(MAX_ITEM);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
    public static String capitalizeFirstLetter(String input){
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static Drawable shimmer(){
        Shimmer shimmer=new Shimmer.AlphaHighlightBuilder()
                .setDuration(1000)
                .setBaseAlpha(0)
                .setIntensity(5)
                .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
                .setAutoStart(true)
                .build();
        ShimmerDrawable drawable=new ShimmerDrawable();
        drawable.setShimmer(shimmer);
        return drawable;
    }

}

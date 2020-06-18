package com.androidlover5852.fetcher.Model;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.LayoutRes;

import com.androidlover5852.fetcher.enums.Method;

import java.util.ArrayList;

public class OptionsBuilder{
    private Context activity;
    private @LayoutRes
    int layout;
    private int maxItem;

    public OptionsBuilder() {}

    public OptionsBuilder setActivity(Context activity) {
        this.activity = activity;
        return this;
    }


    public Context getActivity() {
        return activity;
    }

    public OptionsBuilder setActivity(Activity activity) {
        this.activity = activity;
        return this;
    }

    public int getLayout() {
        return layout;
    }

    public OptionsBuilder setLayout(int layout) {
        this.layout = layout;
        return this;
    }

    public int getMaxItem() {
        return maxItem;
    }

    public OptionsBuilder setMaxItem(int maxItem) {
        this.maxItem = maxItem;
        return this;
    }
}

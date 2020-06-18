package com.androidlover5852.fetcher.Holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private View view;
    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
        this.view=itemView;
    }

    public View getView() {
        return view;
    }
}

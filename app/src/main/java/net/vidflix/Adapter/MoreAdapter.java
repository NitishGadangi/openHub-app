package net.vidflix.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.androidlover5852.fetcher.Adapter.RecyclerBuilder;
import com.androidlover5852.fetcher.Holder.BaseViewHolder;

import net.vidflix.Model.More.MoreModel;
import net.vidflix.R;

public class MoreAdapter extends RecyclerBuilder<MoreModel> {
    private MoreModel moreModel;
    public MoreAdapter(Context context, int layout) {
        super(context, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull MoreModel model, View v) {
        TextView tvTitle=findViewById(R.id.tv_title);
        TextView tvData=findViewById(R.id.tv_data);
        tvTitle.setText(model.getTitle());
        tvData.setText(model.getData());
    }


    @Override
    public void onClick(View view, MoreModel model, int id) {
        moreModel=model;
        if (contain("telegram")){
            telegram();
        }
        else if (contain("updates")){

        }

    }

    private boolean contain(String var){
        String title=moreModel.getTitle();
        if (!TextUtils.isEmpty(title)){
            return title.toLowerCase().contains(var);
        }else return false;
    }

    private void telegram(){
        Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/vidflix_support"));
        getContext().startActivity(intent);
    }
}

package net.vidflix.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.androidlover5852.fetcher.Adapter.RecyclerBuilder;
import com.androidlover5852.fetcher.Holder.BaseViewHolder;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Util;

import net.vidflix.Activity.PlayerActivity;
import net.vidflix.Activity.SeriesActivity;
import net.vidflix.Model.MoviePlayback.VideosItem;
import net.vidflix.Model.Series.EpisodesItem;
import net.vidflix.R;
import net.vidflix.dialog.qualitySelector.Mp4QualitySelectorDialog;
import net.vidflix.task.DecoderTask;
import net.vidflix.task.RedirectTask;

public class EpisodeAdapter extends RecyclerBuilder<EpisodesItem> {
    public EpisodeAdapter(Activity activity, int layout) {
        super(activity, layout);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position, @NonNull EpisodesItem model, View v) {
        TextView tvCategory=findViewById(R.id.tv_title);
        ImageView imgThumbnail=findViewById(R.id.img_thumbnail);

        tvCategory.setText(model.getEpiName());
        String icon= TextUtils.isEmpty(model.getEpiThumb()) ?getCustomData("poster"):model.getEpiThumb();
        if (icon!=null){
            Glide.with(getContext()).load(icon).placeholder(shimmer()).into(imgThumbnail);
        }

    }

    public void play(EpisodesItem model){

        if (model.getUrl().size()>1)
        {
            Mp4QualitySelectorDialog dialog=new Mp4QualitySelectorDialog(getContext(),model.getUrl());
            dialog.setOnDismissListener(d -> {
                loadVideo(dialog.getSelected());
            });
            dialog.show();
        }
        else {
            loadVideo(model.getUrl().get(0));
        }


    }
    private void loadVideo(VideosItem item){
        if (getCustomData("isLive").equals("1"))
        {
            item.setFileUrl(DecoderTask.getInstance().decrypt(item.getUrl(),item.getTimestamp()));
        }else item.setFileUrl(item.getUrl());

        String detailsLicense=getCustomData("license");
        String license=TextUtils.isEmpty(item.getLicenseUrl())?detailsLicense:item.getLicenseUrl();
        item.setLicenseUrl(license);
        item.setAgent(getCustomData("User-Agent"));
        if (Util.inferContentType(Uri.parse(item.getFileUrl()))== C.TYPE_OTHER)
        {
            new RedirectTask(getContext(),item.getFileUrl()).setOnResult(url -> {
                if (url!=null)
                    item.setFileUrl(url);
                rock(item);
            }).execute();
        }else rock(item);
    }
    private void rock(VideosItem item){
        Bundle bundle=new Bundle();
        bundle.putSerializable("VideoItem",item);
        start(PlayerActivity.class,bundle);
    }

    @Override
    public void onClick(View view, EpisodesItem model, int id) {
        play(model);
    }
}
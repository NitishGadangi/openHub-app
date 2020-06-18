package net.vidflix.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlover5852.fetcher.Fetcher.Fetcher;
import com.androidlover5852.fetcher.enums.Method;
import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

import net.vidflix.Model.MoviePlayback.MoviePlayBackModel;
import net.vidflix.Model.MoviePlayback.VideosItem;
import net.vidflix.R;
import net.vidflix.dialog.progress.ProgressDialog;
import net.vidflix.dialog.qualitySelector.Mp4QualitySelectorDialog;
import net.vidflix.task.DecoderTask;
import net.vidflix.task.RedirectTask;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MoviesDetailsActivity extends InitActivity {
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.rv_data)
    RecyclerView rvData;
    @BindView(R.id.spinner_season)
    Spinner spinner;
    @BindView(R.id.playerView)
    RelativeLayout playerView;
    @BindView(R.id.img_thumbnail)
    ImageView imgThumb;

    private MoviePlayBackModel moviePlayBackModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        ButterKnife.bind(this);
        moviePlayBackModel= (MoviePlayBackModel) getIntent().getExtras().getSerializable("MoviesPlaybackData");

        setTitle(moviePlayBackModel.getName());
        hideElements(spinner,rvData);

        String icon=moviePlayBackModel.getIconUrl()==null?moviePlayBackModel.getPosterUrl():moviePlayBackModel.getIconUrl();
        Glide.with(this)
                .load(icon)
                .placeholder(shimmer())
                .into(imgThumb);

        tvDetails.setText(loadHtml(String.format("<h1>%s</h1><p>%s</p><h3>Genres</h3><p>%s</p><h3>Trivia</h3><p>%s</p>",
                moviePlayBackModel.getName(),
                moviePlayBackModel.getReleaseDate(),
                moviePlayBackModel.getGenres(),
                moviePlayBackModel.getDescription()
        )));

       try {
           List<VideosItem> videos= Arrays.asList(new Gson().fromJson(DecoderTask.getInstance().decrypt(moviePlayBackModel.getVideos(),moviePlayBackModel.getTimestamp()),VideosItem[].class));
           playerView.setOnClickListener((view)->startPlayBack(videos));
       }
       catch (Exception e){
           Toast.makeText(this,"Something really bad happened",Toast.LENGTH_SHORT).show();
       }

    }

    private void startPlayBack(List<VideosItem> videosItems){
        if (videosItems.size()>1){
            Mp4QualitySelectorDialog dialog=new Mp4QualitySelectorDialog(this,videosItems);
            dialog.setOnDismissListener(d -> {
                startPlayer(dialog.getSelected());
            });
            dialog.show();
        }
        else startPlayer(videosItems.get(0));
    }
    private void startPlayer(VideosItem item){
        Intent intent=new Intent(this,PlayerActivity.class);
        item.setAgent(moviePlayBackModel.getUserAgent());
        item.setLicenseUrl(moviePlayBackModel.getLicenseUrl());
        intent.putExtra("VideoItem",item);
        //TODO:CLeanup in future
        if (Util.inferContentType(Uri.parse(item.getFileUrl()))== C.TYPE_OTHER)
        {
            new RedirectTask(this,item.getFileUrl()).setOnResult(url -> {
                if (url!=null)
                item.setFileUrl(url);
                startActivity(intent);
            }).execute();
        }else
            startActivity(intent);
    }
}

package net.vidflix.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.se.omapi.Session;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlover5852.fetcher.Fetcher.Fetcher;
import com.androidlover5852.fetcher.Utils.Utils;
import com.androidlover5852.fetcher.enums.Method;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import net.vidflix.Adapter.EpisodeAdapter;
import net.vidflix.Adapter.HomeCategoryAdapter;
import net.vidflix.Adapter.SpinnerAdapter;
import net.vidflix.Model.MoviePlayback.VideosItem;
import net.vidflix.Model.Series.SeasonListModel;
import net.vidflix.Model.Series.SeriesModel;
import net.vidflix.R;
import net.vidflix.Utils.Constant;
import net.vidflix.task.DecoderTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SeriesActivity extends InitActivity implements View.OnClickListener {
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

    private SeriesModel seriesModel;
    private EpisodeAdapter episodeAdapter;
    private List<SeasonListModel> seasonListModels=new ArrayList<>();
    private SpinnerAdapter spinnerAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_series);
        seriesModel= (SeriesModel) getIntent().getExtras().getSerializable("SeriesModel");
        episodeAdapter=new EpisodeAdapter(this,R.layout.holder_home_category);
        spinnerAdapter = new SpinnerAdapter(SeriesActivity.this,
                R.layout.spinner_dropdown,seasonListModels);

        ButterKnife.bind(this);

        String icon=seriesModel.getIconUrl()==null?seriesModel.getPosterUrl():seriesModel.getIconUrl();
        Glide.with(this)
                .load(icon)
                .placeholder(shimmer())
                .into(imgThumb);
        setTitle(seriesModel.getName());

        tvDetails.setText(loadHtml(String.format("<h1>%s</h1><p>%s</p><h3>Genres</h3><p>%s</p><h3>Trivia</h3><p>%s</p><h3>Seasons</h3>",
                seriesModel.getName(),
                seriesModel.getReleaseDate(),
                seriesModel.getGenres(),
                seriesModel.getDescription()
        )));
        if (seriesModel.getIsLive().equals("1"))
        {
            loading(true);
            Fetcher.ref(seriesModel.getLiveFetch()).setMethod(Method.GET).connect(SeasonListModel.class,response -> {
                loading(false);
                seasonListModels=response.getArrayList();
                setList(0);

            });

        }
        else
        {
            seasonListModels= getSeasonListModels(seriesModel.getEpisodesData());
            setList(0);
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (seasonListModels!=null){
                    setList(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        episodeAdapter.addCustomData("license",seriesModel.getLicenseUrl());
        episodeAdapter.addCustomData("isLive",seriesModel.getIsLive());
        episodeAdapter.addCustomData("poster",seriesModel.getPosterUrl()==null?seriesModel.getPosterUrl():seriesModel.getIconUrl());
        episodeAdapter.addCustomData("User-Agent",seriesModel.getUserAgent());
        int mNoOfColumns = calculateNoOfColumns();
        rvData.setLayoutManager(Utils.gridLayoutManager(mNoOfColumns));
        rvData.setAdapter(episodeAdapter);
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(spinnerAdapter);
        playerView.setOnClickListener(this);

    }


    private List<SeasonListModel> getSeasonListModels(String data){
        return Arrays
                .asList(new Gson()
                        .fromJson(DecoderTask.
                                getInstance()
                                .decrypt(data,seriesModel.getTimestamp()),SeasonListModel[].class)
                );
    }
    private void setList(int position){
        if (seasonListModels!=null){
            episodeAdapter.setList(seasonListModels.get(position).getEpisodes());
            spinnerAdapter.clear();
            spinnerAdapter.addAll(seasonListModels);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == playerView.getId()) {
            if (seasonListModels != null) {
                if (seasonListModels.size() != 0 && episodeAdapter!=null) {
                    episodeAdapter.play(seasonListModels.get(0).getEpisodes().get(0));
                }else if (isLoading()){
                    Toast.makeText(SeriesActivity.this,"Please wait while loading...",Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(SeriesActivity.this,"No episodes found...",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
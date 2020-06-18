package net.vidflix.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.androidlover5852.fetcher.Fetcher.Fetcher;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.source.smoothstreaming.SsMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.RandomTrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.ResolvingDataSource;
import com.google.android.exoplayer2.util.Util;

import net.vidflix.Model.MoviePlayback.VideosItem;
import net.vidflix.R;
import net.vidflix.Utils.Constant;
import net.vidflix.Utils.SimplePlayer;
import net.vidflix.datasource.CustomDataSourcesFactory;
import net.vidflix.dialog.qualitySelector.TrackSelectionDialog;
import net.vidflix.task.FetchZeeDrm;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static com.google.android.exoplayer2.Player.STATE_BUFFERING;
import static com.google.android.exoplayer2.Player.STATE_IDLE;
import static com.google.android.exoplayer2.Player.STATE_READY;

public class PlayerActivity extends InitActivity implements Player.EventListener, View.OnClickListener {
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private DashMediaSource dashMediaSource;
    private VideosItem videosItem;
    private ImageView settings;
    private DefaultTrackSelector trackSelector;
    private DataSource.Factory factory;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        videosItem=(VideosItem) getIntent().getExtras().getSerializable("VideoItem");
        playerView=findViewById(R.id.playerView);
        settings=playerView.findViewById(R.id.settings);
        settings.setOnClickListener(this);
        if (videosItem!=null)
        {
            if (!TextUtils.isEmpty(videosItem.getLicenseUrl()))
            {
                if (videosItem.getLicenseUrl()!=null)
                {
                    new FetchZeeDrm(videosItem.getLicenseUrl(),this)
                            .setCallBack(drmSessionManager -> {

                                boolean isHotstar=videosItem.getFileUrl().contains("glamstar");
                                if (isHotstar)
                                factory=new CustomDataSourcesFactory(videosItem.getAgent()==null? Constant.agent:videosItem.getAgent());
                                else factory=buildDataSourceFactory(videosItem.getAgent());
                                dashMediaSource=new DashMediaSource.Factory(factory)
                                        .setDrmSessionManager(drmSessionManager)
                                        .createMediaSource(Uri.parse(videosItem.getFileUrl()));
                                if (isHotstar)
                                {
                                    dashMediaSource.addEventListener(new Handler(),
                                            new MediaSourceEventListener() {
                                                @Override
                                                public void onLoadCompleted(int windowIndex, @Nullable MediaSource.MediaPeriodId mediaPeriodId, LoadEventInfo loadEventInfo, MediaLoadData mediaLoadData) {
                                                    Map<String, List<String>> hashMap=loadEventInfo.responseHeaders;

                                                    if (hashMap!=null && loadEventInfo.uri.toString().contains(".mpd")) {
                                                        if (hashMap.size()>0){
                                                            String hdntl="hdntl";
                                                            if (hashMap.get(hdntl)!=null){

                                                                HashMap<String,String> hash=new HashMap<>();
                                                                hash.put("cookie", hdntl+"="+hashMap.get(hdntl).get(0));
                                                                hash.put(hdntl, hashMap.get(hdntl).get(0));
                                                                ((CustomDataSourcesFactory)factory).setHeaders(hash);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                    );
                                }

                                loadPlayer(dashMediaSource);

                            }).execute();
                }
            }
            else {
                loadPlayer(mediaSource(videosItem.getFileUrl(),videosItem.getAgent()));
            }
        }
        else {
            Toast.makeText(this,"Something went wrong..",Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onDestroy() {
        factory=null;
        dashMediaSource=null;
        super.onDestroy();
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        error.printStackTrace();
        Toast.makeText(PlayerActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
        finish();
    }

    private void showQualityDialog(){
        simplePlayer.pause();
        TrackSelectionDialog trackSelectionDialog =
                TrackSelectionDialog.createForTrackSelector(
                        trackSelector,
                        dismissedDialog -> simplePlayer.resume());

        trackSelectionDialog.show(getSupportFragmentManager(),  null);
    }
    private DefaultTrackSelector trackSelector(){
        TrackSelection.Factory trackSelectionFactory = new RandomTrackSelection.Factory();
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this, trackSelectionFactory);;
        DefaultTrackSelector.ParametersBuilder builder =
                new DefaultTrackSelector.ParametersBuilder(this);
        builder.setMaxVideoSizeSd();

        trackSelector.setParameters(builder.build());
        return trackSelector;
    }

    boolean hasContent;
    private void loadPlayer(MediaSource mediaSource){
        trackSelector=trackSelector();
        player = simplePlayer.setTrackSelector(trackSelector).createPlayer(this);
        player.prepare(mediaSource,true,false);
        player.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        playerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
        playerView.setUseController(true);
        playerView.setKeepScreenOn(true);
        playerView.setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS);
        player.setPlayWhenReady(true);
        playerView.setPlayer(player);
        player.addListener(this);
        settingsTask();
    }

    private void settingsTask(){
        //TODO:Remove UI thread
        AtomicInteger i=new AtomicInteger();
        Timer timer=new Timer();
        TimerTask timerTask=new TimerTask() {
            @Override
            public void run() {
                i.set(i.get()+1);
                if (!hasContent)
                    hasContent=TrackSelectionDialog.willHaveContent(trackSelector);
                if (i.get()==60)
                    timer.cancel();
                if (hasContent){
                    runOnUiThread(()->{
                        settings.setVisibility(View.VISIBLE);
                        playerView.showController();
                    });
                    timer.cancel();

                }
            }
        };
        timer.scheduleAtFixedRate(timerTask,0,1000);

    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==settings.getId()){
            showQualityDialog();
        }
    }
}
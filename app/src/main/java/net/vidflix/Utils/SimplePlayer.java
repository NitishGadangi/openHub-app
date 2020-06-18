package net.vidflix.Utils;

import android.content.Context;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;

public class SimplePlayer {
    private static SimplePlayer instance;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    public static SimplePlayer getInstance() {
        if (instance==null)
            instance=new SimplePlayer();
        return instance;
    }

    public SimplePlayer setTrackSelector(DefaultTrackSelector trackSelector) {
        this.trackSelector = trackSelector;
        return this;
    }

    public SimpleExoPlayer createPlayer(Context context) {
        SimpleExoPlayer.Builder builder=new SimpleExoPlayer.Builder(context);
        if (trackSelector!=null)
        builder.setTrackSelector(trackSelector);
        player=builder.build();
        return player;
    }

    public void destroyPlayer(){
        if (player!=null)
        {
            player.stop();
            player=null;
        }
    }


    public void pause(){
        if (player!=null)
            player.setPlayWhenReady(false);
    }

    public void resume(){
        if (player!=null)
            player.setPlayWhenReady(true);
    }



    public SimpleExoPlayer getPlayer() {
        return player;
    }
}

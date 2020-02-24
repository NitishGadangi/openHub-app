package xxxxxxxxxxx;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FragMovies extends Fragment {

    String deviceId="FAILED";

    RecyclerView rv_allmovies;
    RecyclerView.LayoutManager lm_allmovies;

    ProgressBar prog_frag_movies,prog_pager_movies;

    SharedPreferences bak_json;

    static BackgroundTask backgroundTask;

    Boolean isFirstTime=false;

    String m1_json_movies,m2_json_movies,m3_json_movies,m4_json_movies,m5_json_movies,m6_json_movies;
    int server=1;

    TextView tv_server1,tv_server2,tv_server3,tv_server4,tv_server5,tv_server6;

    int curServer=1;

    int showingPage=0;
    Boolean eof=false,isLoading=false;


    String jsonBackup1="[]",jsonBackup2="[]",jsonBackup3="[]",jsonBackup4="[]",jsonBackup5="[]",jsonBackup6="[]";

    FragMovies(int server){
        this.server=server;
    }
    FragMovies(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView=inflater.inflate(R.layout.frag_movies,container,false);

        
$$\   $$\       $$$$$$\       $$$$$$$\        $$$$$$$\        $$$$$$$$\       $$\   $$\ 
$$ |  $$ |      \_$$  _|      $$  __$$\       $$  __$$\       $$  _____|      $$$\  $$ |
$$ |  $$ |        $$ |        $$ |  $$ |      $$ |  $$ |      $$ |            $$$$\ $$ |
$$$$$$$$ |        $$ |        $$ |  $$ |      $$ |  $$ |      $$$$$\          $$ $$\$$ |
$$  __$$ |        $$ |        $$ |  $$ |      $$ |  $$ |      $$  __|         $$ \$$$$ |
$$ |  $$ |        $$ |        $$ |  $$ |      $$ |  $$ |      $$ |            $$ |\$$$ |
$$ |  $$ |      $$$$$$\       $$$$$$$  |      $$$$$$$  |      $$$$$$$$\       $$ | \$$ |
\__|  \__|      \______|      \_______/       \_______/       \________|      \__|  \__|
                                                                                        
                                                                                        
                                                                                        




}

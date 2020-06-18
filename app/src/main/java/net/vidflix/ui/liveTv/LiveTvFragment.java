package net.vidflix.ui.liveTv;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlover5852.fetcher.Fetcher.Fetcher;
import com.androidlover5852.fetcher.Utils.Utils;
import com.androidlover5852.fetcher.enums.Method;
import com.facebook.shimmer.ShimmerFrameLayout;

import net.vidflix.Activity.InitActivity;
import net.vidflix.Activity.MoviesActivity;
import net.vidflix.Adapter.ChannelsAdapter;
import net.vidflix.Adapter.MoviesAdapter;
import net.vidflix.Model.Movies.MoviesModel;
import net.vidflix.R;
import net.vidflix.Utils.Constant;
import net.vidflix.fragment.InitFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveTvFragment extends InitFragment {
    @BindView(R.id.rv_channels)
    RecyclerView rvData;
    @BindView(R.id.live_progress)
    ProgressBar liveProg;
    @BindView(R.id.live_fb)
    ShimmerFrameLayout fbs;

    private MoviesAdapter channelsAdapter;
    private boolean loading,is_pagination;
    private InitActivity initActivity;
    int i=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_live_tv, container, false);
        ButterKnife.bind(this,root);
        initActivity= (InitActivity) getActivity();
        channelsAdapter=new MoviesAdapter(getContext(),R.layout.holder_home_category);
        getInitActivity().enableBack(false);
        getInitActivity().showToolbar(true);
        getInitActivity().setTitle("Live TV");
        getInitActivity().loading(true);
        getInitActivity().showStatusBar();
        rvData.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    final int visibleThreshold = 3;

                    GridLayoutManager layoutManager = (GridLayoutManager)rvData.getLayoutManager();
                    int lastItem  = layoutManager.findLastCompletelyVisibleItemPosition();
                    int currentTotalCount = layoutManager.getItemCount();

                    if(currentTotalCount <= lastItem + visibleThreshold){
                        loading = false;
                        i++;
                        fetch(i);
                    }

                }
            }
        });
        int mNoOfColumns = initActivity.calculateNoOfColumns();
        rvData.setLayoutManager(Utils.gridLayoutManager(mNoOfColumns));
        rvData.setAdapter(channelsAdapter);

        Animation fade = AnimationUtils.loadAnimation(getContext(), R.anim.slide_in_up);
        rvData.setAnimation(fade);

        fetch(0);

        return root;
    }

    private void fetch(int page){
        String url;
        if (page!=0)
        {
            url= Constant.live_TV +"&page="+page;
            loading=true;
        }
        else url=Constant.live_TV;

        Fetcher.ref(url).setMethod(Method.GET).connect(MoviesModel.class, response -> {
            getInitActivity().loading(false);
            if (response.getObject()!=null){
                if (channelsAdapter.getArrayList()!=null)
                    channelsAdapter.addItems(response.getObject().getContent());
                else channelsAdapter.setList(response.getObject().getContent());
            }
            liveProg.setVisibility(View.GONE);
            fbs.setVisibility(View.GONE);
        });

    }

}
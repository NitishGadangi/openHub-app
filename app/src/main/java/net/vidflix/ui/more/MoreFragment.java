package net.vidflix.ui.more;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlover5852.fetcher.Utils.Utils;
import com.google.android.exoplayer2.util.Util;
import com.google.android.material.card.MaterialCardView;

import net.vidflix.Adapter.MoreAdapter;
import net.vidflix.BuildConfig;
import net.vidflix.Model.More.MoreModel;
import net.vidflix.R;
import net.vidflix.fragment.InitFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreFragment extends InitFragment {

    private MoreViewModel moreViewModel;

    @BindView(R.id.rv_more)
    RecyclerView rvMore;
    @BindView(R.id.about_imageView)
    ImageView imageView;
    private MoreAdapter adapter;
    private List<MoreModel> moreModels=new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        moreViewModel =
                new ViewModelProvider(this).get(MoreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_more, container, false);
        adapter=new MoreAdapter(getContext(),R.layout.holder_more);
        ButterKnife.bind(this,root);
        getInitActivity().enableBack(false);
        getInitActivity().showToolbar(true);
        getInitActivity().setTitle("More");
        getInitActivity().loading(false);
        getInitActivity().showStatusBar();

        Animation slide_from_top = AnimationUtils.loadAnimation(getContext(),R.anim.item_animation_fall_down);
        imageView.startAnimation(slide_from_top);
        Animation slide_from_left = AnimationUtils.loadAnimation(getContext(),R.anim.slide_in_left);
        rvMore.startAnimation(slide_from_left);
        rvMore.setLayoutManager(Utils.linear(RecyclerView.VERTICAL));
        rvMore.setAdapter(adapter);
        Items();
        return root;
    }

    private void Items(){
        MoreModel[] moreModel=new MoreModel[]{
                new MoreModel("APP","Version\n"+ BuildConfig.FAKE_VERSION),
                new MoreModel("COPYRIGHT","Copyright @VIDFLIX All rights reserved\nTerms and Policies"),
                new MoreModel("Changelog","Checkout what's new"),
                new MoreModel("Check for Updates","Get latest available apk"),
                new MoreModel("Telegram","Join to report issues and request a feature"),
        };
        moreModels=Arrays.asList(moreModel);
        adapter.setList(moreModels);
    }
}

package net.vidflix.dialog.update;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.androidlover5852.fetcher.Adapter.ListAdapter;
import com.google.android.material.card.MaterialCardView;

import net.vidflix.Model.Update.UpdateModel;
import net.vidflix.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpdateDialog extends Dialog {
    @BindView(R.id.tv_title)
    TextView title;
    @BindView(R.id.list_changelog)
    ListView changeLog;
    Base adapter;
    public UpdateDialog(@NonNull Context context,UpdateModel updateModel) {
        super(context);
        setContentView(R.layout.dialog_update);
        //TODO:FX HOLDER LATER
        adapter=new Base(getContext(),R.layout.holder_home_category);
        setCancelable(false);
        ButterKnife.bind(this);
        adapter.setArrayList(updateModel.getChangeLogs());
        changeLog.setAdapter(adapter);
        title.setText(updateModel.getDialogTitle());
        show();
    }

    private class Base extends ListAdapter<String>{

        public Base(Context context, int layout) {
            super(context, layout);
        }

        @Override
        public void getView(int i, @NonNull String s, View v, ViewGroup viewGroup) {
            TextView textView=v.findViewById(R.id.tv_title);
            MaterialCardView cardView=v.findViewById(R.id.card_thumb);
            textView.setText(s);
            cardView.setVisibility(View.GONE);
        }
    }
}

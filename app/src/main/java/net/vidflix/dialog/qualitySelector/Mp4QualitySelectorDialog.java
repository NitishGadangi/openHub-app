package net.vidflix.dialog.qualitySelector;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlover5852.fetcher.Adapter.RecyclerBuilder;
import com.androidlover5852.fetcher.Holder.BaseViewHolder;
import com.androidlover5852.fetcher.Utils.Utils;
import com.google.android.exoplayer2.ui.TrackSelectionView;

import net.vidflix.Model.MoviePlayback.VideosItem;
import net.vidflix.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Mp4QualitySelectorDialog extends Dialog implements View.OnClickListener {

    private VideosItem selected;
    @BindView(R.id.bt_cancel)
    Button btCancel;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    private DialogInterface.OnDismissListener onDismissListener;

    public Mp4QualitySelectorDialog(@NonNull Context context,List<VideosItem> videosItems) {
        super(context);
        setContentView(R.layout.dialog_mp4_selector);
        ButterKnife.bind(this);

        radioGroup.removeAllViews();
        for (int i = 0; i < videosItems.size(); i++) {
            VideosItem item=videosItems.get(i);
            if (item.getFileUrl()!=null || item.getUrl()!=null)
            {
                RadioButton radioButton=new RadioButton(context);
                radioButton.setText(item.getLabel());
                radioButton.setId(i);
                radioGroup.addView(radioButton,i);
            }
        }

        setCancelable(false);
        btCancel.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            selected=videosItems.get(checkedId);

            onDismissListener.onDismiss(Mp4QualitySelectorDialog.this);
            dismiss();
        });
    }

    @Override
    public void setOnDismissListener(OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    public VideosItem getSelected() {
        return selected;
    }


    @Override
    public void onClick(View v) {
        int id=v.getId();
        if (id==btCancel.getId()){
            onDismissListener.onDismiss(Mp4QualitySelectorDialog.this);
            dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        onDismissListener.onDismiss(Mp4QualitySelectorDialog.this);
        dismiss();
    }
}

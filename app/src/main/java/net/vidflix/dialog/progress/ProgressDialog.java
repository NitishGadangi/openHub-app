package net.vidflix.dialog.progress;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import net.vidflix.R;

public class ProgressDialog extends Dialog {
    public ProgressDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.dialog_progress);
        setCancelable(false);
        show();
    }
}

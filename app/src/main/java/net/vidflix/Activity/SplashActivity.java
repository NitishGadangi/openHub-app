package net.vidflix.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.loader.content.Loader;

import net.vidflix.BuildConfig;
import net.vidflix.MainActivity;
import net.vidflix.R;

public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;
    private ConstraintLayout constraintLayout;
    private TextView ver;
    static  {
        System.loadLibrary("librtmp-jni");
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        constraintLayout = findViewById(R.id.splash_container);
        ver=findViewById(R.id.version);
        ver.setText( BuildConfig.FAKE_VERSION);
        casdAA();
        Animation fade = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        constraintLayout.startAnimation(fade);
        if (!isNetworkAvailable()) {
            new AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
                    .setTitle("Not Connected to internet?")
                    .setMessage("This app requires active internet connection. Please check your internet connection and open again.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Restart();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .show();

        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }
    public void Restart()
    {
        this.recreate();
    }
    private native String casdAA();

}

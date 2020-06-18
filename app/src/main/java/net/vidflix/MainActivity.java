package net.vidflix;

import android.os.Bundle;
import android.util.Base64;

import com.androidlover5852.fetcher.Fetcher.Fetcher;
import com.androidlover5852.fetcher.enums.Method;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onesignal.OneSignal;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import net.vidflix.Activity.InitActivity;
import net.vidflix.Model.Update.UpdateModel;
import net.vidflix.Utils.Constant;
import net.vidflix.dialog.update.UpdateDialog;
import net.vidflix.task.DecoderTask;

import java.util.HashMap;


public class MainActivity extends InitActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
            Fetcher.ref(Constant.update).setMethod(Method.GET).connect(UpdateModel.class, response -> {
                if (response.getObject()!=null)
                {
                    if (response.getObject().getMsg().equals("New Update Found"))
                        new UpdateDialog(MainActivity.this,response.getObject());
                }
            });

    }
}

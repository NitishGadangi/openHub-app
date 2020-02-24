package nitish.vidflix1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

public class MorePage extends AppCompatActivity {

    ProgressDialog progressDialog;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left , R.anim.slide_out_right);
    }

    void btmButtons(){

        findViewById(R.id.img_home_btm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //------Animation-----------//
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(1000);
                view.startAnimation(animation1);
                //-------------------------//
                onBackPressed();
//                startActivity(new Intent(getApplicationContext(),MainActivity.class));
//                finish();
//                overridePendingTransition(R.anim.slide_in_left , R.anim.slide_out_right);
            }
        });
        findViewById(R.id.img_search_btm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //------Animation-----------//
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(1000);
                view.startAnimation(animation1);
                //-------------------------//
                startActivity(new Intent(getApplicationContext(),Search.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left , R.anim.slide_out_right);
            }
        });
        findViewById(R.id.img_more_btm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //------Animation-----------//
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(1000);
                view.startAnimation(animation1);
                //-------------------------//
//                startActivity(new Intent(getApplicationContext(),MorePage.class));
//                finish();
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        findViewById(R.id.img_down_btm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //------Animation-----------//
                Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
                animation1.setDuration(1000);
                view.startAnimation(animation1);
                //-------------------------//
                startActivity(new Intent(getApplicationContext(),LiveTvPage.class));
                finish();
                overridePendingTransition(R.anim.slide_in_left , R.anim.slide_out_right);
            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_page);

        progressDialog = new ProgressDialog(MorePage.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        btmButtons();
    }

    public void downloads(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        Uri selectedUri = Uri.parse(Environment.DIRECTORY_DOWNLOADS+"/VIDFLIX");
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(selectedUri, "resource/folder");

        if (intent.resolveActivityInfo(getPackageManager(), 0) != null)
        {
            startActivity(intent);
        }
        else
        {
            Snackbar.make(findViewById(android.R.id.content),"Please install ant third-party file browser",Snackbar.LENGTH_LONG).show();
//                    Toast.makeText(getApplicationContext(), "Please Install a File Manager.", Toast.LENGTH_SHORT).show();
        }

    }

    public void copyrights(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        Intent intent=new Intent(getApplicationContext(),WebDisplay.class);
        intent.putExtra("HEADER","Copyrights");
        intent.putExtra("URL","https://vidflix.net/copyright.html");
        startActivity(intent);

    }

    public void changelog(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        Intent intent=new Intent(getApplicationContext(),WebDisplay.class);
        intent.putExtra("HEADER","ChangeLog");
        intent.putExtra("URL","https://vidflix.net/app/changelog");
        startActivity(intent);
    }
    public void checkUpdates(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        new UpdaterChecker().execute();
    }
    public void termsPolicies(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        Intent intent=new Intent(getApplicationContext(),WebDisplay.class);
        intent.putExtra("HEADER","Terms and Policy");
        intent.putExtra("URL","https://vidflix.net/termsnpolicy.html");
        startActivity(intent);
    }
    public void website(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        String url = "https://vidflix.net";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void telegram(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        String url = "https://t.me/vidflix_support";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void email(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        final String pack = "com.google.android.gm";
// return true if gmail is installed
        boolean isGmailInstalled = isAppInstalled(MorePage.this, pack);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[] {"support@vidflix.net"});
        if (isGmailInstalled) {
            intent.setType("text/html");
            intent.setPackage(pack);
            startActivity(intent);
        } else {  // allow user to choose a different app to send email with
            intent.setType("message/rfc822");
            startActivity(Intent.createChooser(intent, "choose an email client"));
        }
    }
    public void team(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        String url = "https://vidflix.net/team";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
    public void apps(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        String url = "https://vidflix.net/our_apps";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    public void nitish_me(View view){
        //------Animation-----------//
        Animation animation1 = new AlphaAnimation(0.3f, 1.0f);
        animation1.setDuration(1000);
        view.startAnimation(animation1);
        //-------------------------//

        String url = "https://nitishgadangi.github.io/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    class UpdaterChecker extends AsyncTask<Void,Void,Void>{
        JSONObject updateStuff;
        String msg,url,changelog,error;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
            if (error.equals("true")){
                new AlertDialog.Builder(MorePage.this,R.style.MyAlertDialogStyle)
                        .setTitle(msg)
                        .setMessage(changelog)
                        .setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse(url));
                                startActivity(intent);
                            }
                        })
                        .setIcon(R.drawable.ic_stop_hand)
                        .setCancelable(false)
                        .show();
            }else {
                Toast.makeText(MorePage.this, "No update Found", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                updateStuff = DataHandlers.checkUpdate(Long.toString(getVersionCode()),DataHandlers.secret_key);
                error=updateStuff.getString("error");
                if (error.equals("true")){
                    url=updateStuff.getString("url");
                    changelog=updateStuff.getString("change_log");
                    msg=updateStuff.getString("msg");
                }
            } catch (Exception e) {
                error="false";
                e.printStackTrace();
            }
            return null;
        }
    }

    long getVersionCode() throws PackageManager.NameNotFoundException {
        long versionCode=2;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            versionCode=versionCode=getApplicationContext().getPackageManager().getPackageInfo(getPackageName(),0).getLongVersionCode();
        else
            versionCode=getApplicationContext().getPackageManager().getPackageArchiveInfo(getPackageName(),0).versionCode;
        return versionCode;
    }


    boolean isAppInstalled(Context context, String packageName) {
        try {
            context.getPackageManager().getApplicationInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}

package net.vidflix.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import net.vidflix.dialog.progress.ProgressDialog;

import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//TODO:Remove in BETA 3
public class RedirectTask extends AsyncTask<Void,Void,Void> {
    Context context;
    private String resultUrl;
    private String itemUrl;
    private OnResult onResult;
    private ProgressDialog progressDialog;
    public RedirectTask(Context context,String itemUrl){
        this.context=new WeakReference<>(context).get();
        this.itemUrl=itemUrl;
        progressDialog=new ProgressDialog(context);
        progressDialog.show();
    }

    public RedirectTask setOnResult(OnResult onResult) {
        this.onResult = onResult;
        return this;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(itemUrl)
                .method("GET",null)
                .build();
        try {
            Response response = client.newCall(request).execute();
            resultUrl=response.networkResponse().request().url().uri().toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
            return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (resultUrl==null)
            Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
        onResult.result(resultUrl);
    }

    public interface OnResult{
        void result(String url);
    }
}

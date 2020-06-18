package net.vidflix.task;

import android.content.Context;
import android.os.AsyncTask;

import com.google.android.exoplayer2.drm.DefaultDrmSessionManager;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.ExoMediaCrypto;
import com.google.android.exoplayer2.drm.ExoMediaDrm;
import com.google.android.exoplayer2.drm.HttpMediaDrmCallback;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import static net.vidflix.Utils.Constant.agent;


public class FetchZeeDrm extends AsyncTask<Void,Void,Void>{
    private Context context;
    private String licenseUrl;
    private CallBack callBack;
    private DrmSessionManager<ExoMediaCrypto> drmSessionManager;
    public FetchZeeDrm(String licenseUrl,Context context) {
        this.licenseUrl=licenseUrl;
        this.context= new WeakReference<>(context).get();
    }

    public FetchZeeDrm setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        try {
            ExoMediaDrm.KeyRequest keyRequest=new ExoMediaDrm.KeyRequest(readAsset("content.data"),licenseUrl);
            HttpMediaDrmCallback drmCallback =
                    new HttpMediaDrmCallback(licenseUrl, new DefaultHttpDataSourceFactory(agent));
            drmCallback.executeKeyRequest(Util.getDrmUuid("application/octet-stream"),keyRequest);

            drmSessionManager = new DefaultDrmSessionManager
                    .Builder()
                    .build(drmCallback);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (callBack!=null)
            callBack.result(drmSessionManager);

    }

    private byte[] readAsset(String assetName){

        InputStream inputStream ;
        try {
            inputStream = context.getAssets().open(assetName);
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
            return output.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[]{};
        }

    }

    public interface CallBack{
        void result(DrmSessionManager drmSessionManager);
    }
}

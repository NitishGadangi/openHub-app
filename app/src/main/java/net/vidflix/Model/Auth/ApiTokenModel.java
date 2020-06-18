package net.vidflix.Model.Auth;

import android.util.Base64;

import androidx.annotation.NonNull;

import net.vidflix.BuildConfig;
import net.vidflix.task.DecoderTask;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

public class ApiTokenModel extends HashMap<String,String> implements Serializable {
    private String timestamp=String.valueOf(new Date().getTime());


    public ApiTokenModel() {
        DecoderTask decoderTask=DecoderTask.getInstance();
        byte[] sec=Base64.decode(decoderTask.decrypt(sec()),Base64.DEFAULT);
        byte[] salt=Base64.decode(decoderTask.decrypt(salt()),Base64.DEFAULT);
        String version= DecoderTask
                .getInstance()
                .encrypt(Base64.decode(varSZION(),Base64.DEFAULT),BuildConfig.VERSION_NAME.getBytes());
        put("api-auth".toUpperCase(),sha(decoderTask.addBytes(timestamp.getBytes().length+sec.length+salt.length,timestamp.getBytes(),sec,salt)));
        put("timestamp".toUpperCase(),String.valueOf(timestamp));
        put("User-Agent","vidflix/1.0");
        put("app-key".toUpperCase(),"zPhLKHRI8Qlo20W6nq3DDr2dXVJgQ4EFMwfSBGFgy6Y=");
        put("oauth".toUpperCase(),sha(decoderTask.addBytes(timestamp.getBytes().length+BuildConfig.APPLICATION_ID.getBytes().length+salt.length, timestamp.getBytes(),BuildConfig.APPLICATION_ID.getBytes(),salt)));
        put("app-upd-version".toUpperCase(),version);
    }

    private String sha(byte[] data){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            return Base64.encodeToString(hash,Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }

    private native String sec();
    private native String salt();
    private native String varSZION();
}

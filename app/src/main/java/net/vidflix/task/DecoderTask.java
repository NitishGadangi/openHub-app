package net.vidflix.task;

import android.util.Base64;

import net.vidflix.Utils.Constant;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DecoderTask {
    private static DecoderTask instance;

    public static DecoderTask getInstance() {
        if (instance==null)
            instance=new DecoderTask();
        return instance;
    }

    public String decrypt(String text,long time){
        byte[] fullData=Base64.decode(text,Base64.DEFAULT);
        byte[] data=copyToNewArray(fullData,7,fullData.length-7);
        byte[] kk=forty8(copyToNewArray(fullData,0,7),String.valueOf(time-1200).getBytes());
        byte[] key=copyToNewArray(kk,0,32);
        byte[] iv=copyToNewArray(kk,32,16);

        return decrypt(key,iv,data);
    }

    private byte[] copyToNewArray(byte[]srcArray,int srcPos,int size){
        byte[] destArray=new byte[size];

        System.arraycopy(srcArray, srcPos, destArray, 0, destArray.length);
        return destArray;
    }

    private byte[] forty8(byte[] bit,byte[] time){
        byte[] sec= decrypt(mali().getBytes(),toAo()).getBytes();
        byte[] hash_data=md5(addBytes(bit.length+time.length+sec.length,bit,sec,time));
        byte[] finalData=hash_data;

        while (finalData.length<48){
            byte[] hash_data1 = md5(addBytes(hash_data.length+bit.length,hash_data,bit));
            finalData = addBytes(finalData.length+hash_data1.length,finalData,hash_data1);
        }
        return finalData;

    }

    private String decrypt(byte[] key, byte[] initVector, byte[] encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);
            SecretKeySpec skeySpec = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(encrypted);

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public String encrypt(byte[] raw, byte[] clear) {
        try {
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

            byte[] encrypted = cipher.doFinal(clear);
            return Base64.encodeToString(encrypted,Base64.DEFAULT);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(byte[] raw, String en)  {
        byte[] encrypted=Base64.decode(en,Base64.DEFAULT);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] decrypted = cipher.doFinal(encrypted);
            return new String(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String decrypt(String en) {
        return decrypt(mali().getBytes(),en);
    }

    public  String md5String(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    private byte[] md5(byte[] input){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(input);
            return messageDigest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] addBytes(int size,byte[]... data){
        byte[] combined=new byte[size];
        ByteBuffer buff = ByteBuffer.wrap(combined);
        for (byte[] datum : data) {
            buff.put(datum);
        }
        return combined;
    }

    private native String mali();
    private native String toAo();
}

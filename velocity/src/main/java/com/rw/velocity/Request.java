package com.rw.velocity;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.http.X509TrustManagerExtensions;
import android.os.SystemClock;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * velocity-android
 * <p>
 * Created by ravindu on 15/12/16.
 * Copyright © 2016 Vortilla. All rights reserved.
 */

class Request
{
    final RequestBuilder mBuilder;

    HttpURLConnection mConnection = null;
    StringBuilder mResponse = new StringBuilder();
    private Bitmap mResponseImage = null;
    int mResponseCode = 0;


    Request(RequestBuilder builder)
    {
        mBuilder = builder;
    }


    /**
     * DO NOT CALL THIS DIRECTLY
     * Execute the network requet and post the response.
     * This function will be called from a worker thread from within the Threadpool
     */
    void execute()
    {
        long t = SystemClock.elapsedRealtime();

        if(mBuilder.mocked || Velocity.Settings.GLOBAL_MOCK)
        {
            try
            {
                Thread.sleep(Velocity.Settings.MOCK_RESPONSE_TIME);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }

            NetLog.d(padRight("MOCKED/" + mBuilder.requestMethod, 10) + " : " + padLeft((SystemClock.elapsedRealtime() - t) + "ms : ", 10) + mBuilder.url);
            returnMockResponse();
        }
        else
        {

            boolean success = initializeConnection();

            if (success)
            {
                success = readResponse();

                if (!success)
                    readError();
            }

            NetLog.d(padRight(mResponseCode + "/" + mBuilder.requestMethod, 10) + " : " + padLeft((SystemClock.elapsedRealtime() - t) + "ms : ", 10) + mBuilder.url);
            returnResponse(success);
        }
    }

    private String padLeft(String s, int n)
    {
        return String.format("%1$" + n + "s", s);
    }

    private String padRight(String s, int n)
    {
        return String.format("%1$-" + n + "s", s);
    }

    void setupRequestHeaders()
    {
        mConnection.setRequestProperty("User-Agent", Velocity.Settings.USER_AGENT);

        if((mBuilder.contentType != null && !mBuilder.contentType.equalsIgnoreCase(Velocity.ContentType.TEXT.toString())) || mBuilder.compressed)
            mConnection.setRequestProperty("Content-Type", mBuilder.contentType);

        if(Velocity.Settings.GZIP_ENABLED)
            mConnection.setRequestProperty("Accept-Encoding", "gzip,deflate");


        if (!mBuilder.headers.isEmpty())
        {
            for (String key : mBuilder.headers.keySet())
                mConnection.setRequestProperty(key, mBuilder.headers.get(key));
        }
    }

    void setupRequestBody() throws IOException
    {
        if (mBuilder.requestMethod.equalsIgnoreCase("GET") || mBuilder.requestMethod.equalsIgnoreCase("COPY") || mBuilder.requestMethod.equalsIgnoreCase("HEAD")
                || mBuilder.requestMethod.equalsIgnoreCase("PURGE") || mBuilder.requestMethod.equalsIgnoreCase("UNLOCK"))
        {
            //do not send params for these request methods
            return;
        }

        mConnection.setDoInput(true);
        mConnection.setDoOutput(true);

        if (!mBuilder.params.isEmpty())
        {
            mBuilder.rawParams = getFormattedParams();
        }

        if (mBuilder.rawParams != null)
        {
            if(mBuilder.contentType != null && mBuilder.contentType.equalsIgnoreCase(Velocity.ContentType.FORM_DATA_MULTIPART.toString()))
            {
                DataOutputStream dos = new DataOutputStream(mConnection.getOutputStream());
                for(String param : mBuilder.params.keySet())
                {
                    String val = mBuilder.params.get(param);

                    dos.writeBytes(Velocity.Settings.TWOHYPHENS + Velocity.Settings.BOUNDARY + Velocity.Settings.LINEEND);
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + param + "\"" + Velocity.Settings.LINEEND);
                    dos.writeBytes("Content-Type: text/plain" + Velocity.Settings.LINEEND);
                    dos.writeBytes(Velocity.Settings.LINEEND);
                    dos.writeBytes(val);
                    dos.writeBytes(Velocity.Settings.LINEEND);
                }

                dos.writeBytes(Velocity.Settings.TWOHYPHENS + Velocity.Settings.BOUNDARY + Velocity.Settings.TWOHYPHENS + Velocity.Settings.LINEEND);

                dos.flush();
                dos.close();
            }
            else
            {
                OutputStream os = mConnection.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(mBuilder.rawParams);
                writer.flush();
                writer.close();
                os.close();
            }
        }
    }

    private String getFormattedParams() throws UnsupportedEncodingException
    {
        StringBuilder params = new StringBuilder();
        boolean first = true;

        for (String key : mBuilder.params.keySet())
        {
            if (first)
                first = false;
            else
                params.append("&");

            params.append(URLEncoder.encode(key, "UTF-8"));
            params.append("=");
            params.append(URLEncoder.encode(mBuilder.params.get(key), "UTF-8"));
        }

        return params.toString();
    }



    private boolean initializeConnection()
    {
        boolean ret;

        try
        {
            mResponseCode = makeConnection();

            int count = 0;
            while((mResponseCode == HttpURLConnection.HTTP_MOVED_PERM || mResponseCode == HttpURLConnection.HTTP_MOVED_TEMP) && count < Velocity.Settings.MAX_REDIRECTS)
            {
                count++;

                URL u = new URL(mBuilder.url);
                String location = mConnection.getHeaderField("Location");

                if(location.startsWith("/"))    //relative redirect
                    mBuilder.url = u.getProtocol() + "://" + u.getHost() + location;
                else
                    mBuilder.url = location;

                NetLog.d(mResponseCode + "/redirected : " + mBuilder.url);

                mResponseCode = makeConnection();
            }

            ret = true;
        }
        catch (IOException ioe)
        {
            ret = false;
            mResponse = new StringBuilder(ioe.toString());
        }

        return ret;
    }

    private int makeConnection() throws IOException
    {

        URL url = new URL(mBuilder.url);

        if (url.getProtocol().equalsIgnoreCase("https"))
            mConnection = (HttpsURLConnection) url.openConnection();
        else
            mConnection = (HttpURLConnection) url.openConnection();

        mConnection.setRequestMethod(mBuilder.requestMethod);
        mConnection.setConnectTimeout(Velocity.Settings.TIMEOUT);
        mConnection.setReadTimeout(Velocity.Settings.READ_TIMEOUT);

        setupRequestHeaders();

        setupRequestBody();

        mConnection.connect();
        if (url.getProtocol().equalsIgnoreCase("https") && mBuilder.certs !=null)
            if (mBuilder.certs.size()>0)
            {
                validatePinning(trustManager(), (HttpsURLConnection) mConnection, mBuilder.certs);
            }

        return mConnection.getResponseCode();
    }

    private X509TrustManagerExtensions trustManager(){
        X509TrustManagerExtensions trustManagerExt;
        try {
            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(
                            TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init((KeyStore) null);
            X509TrustManager x509TrustManager = null;
            for (TrustManager trustManager : trustManagerFactory.getTrustManagers()) {
                if (trustManager instanceof X509TrustManager) {
                    x509TrustManager = (X509TrustManager) trustManager;
                    break;
                }
            }
            trustManagerExt = new X509TrustManagerExtensions(x509TrustManager);


            return trustManagerExt;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    private void validatePinning(
            X509TrustManagerExtensions trustManagerExt,
            HttpsURLConnection conn, Set<String> validPins)
            throws SSLException {
        String certChainMsg = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            List<X509Certificate> trustedChain = trustedChain(trustManagerExt, conn);
            for (X509Certificate cert : trustedChain) {
                byte[] publicKey = cert.getPublicKey().getEncoded();
                md.update(publicKey, 0, publicKey.length);
                String pin = Base64.encodeToString(md.digest(), Base64.NO_WRAP);
                certChainMsg += "    sha256/" + pin + " : " + cert.getSubjectDN().toString() + "\n";

                if (validPins.contains(pin)) {
                    System.out.println(pin);
                    return;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new SSLException(e);
        }    throw new SSLPeerUnverifiedException("Certificate pinning " +
                "failure\n  Peer certificate chain:\n" + certChainMsg);
    }
    private List<X509Certificate> trustedChain(
        X509TrustManagerExtensions trustManagerExt,
        HttpsURLConnection conn) throws SSLException {
    Certificate[] serverCerts = conn.getServerCertificates();
    X509Certificate[] untrustedCerts = Arrays.copyOf(serverCerts,
            serverCerts.length, X509Certificate[].class);    String host = conn.getURL().getHost();
    try {
        return trustManagerExt.checkServerTrusted(untrustedCerts,
                "RSA", host);
    } catch (CertificateException e) {
        throw new SSLException(e);
    }
}

    boolean readResponse()
    {
        boolean ret;

        try
        {
            if (mResponseCode / 100 == 2) //all 2xx codes are OK
            {
                InputStream in = mConnection.getInputStream();
                if(mConnection.getHeaderField("Content-Encoding") != null && mConnection.getHeaderField("Content-Encoding").contains("gzip"))
                {
                    in = new GZIPInputStream(in);
                }

                if (mConnection.getContentType() != null && mConnection.getContentType().startsWith("image"))
                {
                    mResponseImage = BitmapFactory.decodeStream(in);
                    mResponse.append(mConnection.getContentType());
                }
                else
                {
                    BufferedInputStream inStream = new BufferedInputStream(in);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));

                    String line;
                    while ((line = reader.readLine()) != null)
                    {
                        mResponse.append(line);
                    }
                }

                ret = true;
            }
            else
                ret = false;
        }
        catch (IOException e)
        {
            ret = false;
            mResponse = new StringBuilder(e.toString());
        }

        return ret;
    }

    private void readError()
    {
        InputStream in = new BufferedInputStream(mConnection.getErrorStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        try
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                mResponse.append(line);
            }
        }
        catch (IOException e)
        {
            mResponse.append(e.toString());
        }

    }

    private void returnMockResponse()
    {
        final Velocity.Response reply = new Velocity.Response(
                true,
                mBuilder.requestId,
                mBuilder.mockResponse,
                200,
                null,
                null,
                mBuilder.userData,
                mBuilder);

        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                if (mBuilder.callback != null)
                {
                    mBuilder.callback.onVelocityResponse(reply);
                }
                else
                    NetLog.d("Warning: No Data callback supplied");
            }
        };

        ThreadPool.getThreadPool().postToUiThread(r);
    }

    private void returnResponse(final boolean success)
    {
        final Velocity.Response reply = new Velocity.Response(
                success,
                mBuilder.requestId,
                mResponse.toString(),
                mResponseCode,
                (mConnection == null) ? null : mConnection.getHeaderFields(),
                mResponseImage,
                mBuilder.userData,
                mBuilder);

        Runnable r = new Runnable()
        {
            @Override
            public void run()
            {
                if (mBuilder.callback != null)
                {
                    if(!success)
                        NetLog.conError(reply, null);

                    mBuilder.callback.onVelocityResponse(reply);
                }
                else
                    NetLog.d("Warning: No Data callback supplied");
            }
        };

        ThreadPool.getThreadPool().postToUiThread(r);
    }
}

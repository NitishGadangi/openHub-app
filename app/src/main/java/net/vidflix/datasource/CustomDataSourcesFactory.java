package net.vidflix.datasource;

import android.net.Uri;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;

import net.vidflix.Utils.Constant;

import java.util.HashMap;
import java.util.Map;

public class CustomDataSourcesFactory extends HttpDataSource.BaseFactory{
    private final String userAgent;
    private final @Nullable
    TransferListener listener;
    private final int connectTimeoutMillis;
    private final int readTimeoutMillis;
    private final boolean allowCrossProtocolRedirects;
    private HashMap<String,String> headers;
    public CustomDataSourcesFactory(String userAgent) {
        this(userAgent, null);
    }

    
    public CustomDataSourcesFactory(String userAgent, @Nullable TransferListener listener) {
        this(userAgent, listener, DefaultHttpDataSource.DEFAULT_CONNECT_TIMEOUT_MILLIS,
                DefaultHttpDataSource.DEFAULT_READ_TIMEOUT_MILLIS, false);
    }
    
    public CustomDataSourcesFactory(
            String userAgent,
            @Nullable TransferListener listener,
            int connectTimeoutMillis,
            int readTimeoutMillis,
            boolean allowCrossProtocolRedirects) {
        this.userAgent = userAgent;
        this.listener = listener;
        this.connectTimeoutMillis = connectTimeoutMillis;
        this.readTimeoutMillis = readTimeoutMillis;
        this.allowCrossProtocolRedirects = allowCrossProtocolRedirects;
    }

    public void setHeaders(HashMap<String,String> hashMap){
        headers=hashMap;
        if (headers.size()>0)
        {
            for (Map.Entry<String, String> stringStringEntry : headers.entrySet()) {
                getDefaultRequestProperties().set(stringStringEntry.getKey(),stringStringEntry.getValue());
            }
        }
    }

    @Override
    protected HttpDataSource createDataSourceInternal(
            HttpDataSource.RequestProperties defaultRequestProperties) {
        DefaultHttpDataSource defaultHttpDataSource = new DefaultHttpDataSource(userAgent, connectTimeoutMillis,
                readTimeoutMillis, allowCrossProtocolRedirects, defaultRequestProperties);

        return defaultHttpDataSource;
    }
}
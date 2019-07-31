package mfp.com.nytimesnewssearch.service;

import android.content.Context;

import java.io.File;
import java.util.concurrent.TimeUnit;

import mfp.com.nytimesnewssearch.network.InternetListener;
import mfp.com.nytimesnewssearch.network.NetworkInterceptor;
import mfp.com.nytimesnewssearch.network.NetworkUtil;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class RetrofitClientInstance {
    private static Retrofit retrofit;
    private static Context context;
    private static InternetListener listener;

    public static final int DISK_CACHE_SIZE = 10 * 1024 * 1024; // 10 MB

    public static final String BASE_URL = "https://api.nytimes.com/svc/search/v2/";

    public static void setInternetListener(InternetListener l) {
        listener = l;
    }

    public static void removeInternetListener() {
        listener = null;
    }

    public static Retrofit getRetrofitInstance(Context c) {
        context = c;
        if (retrofit == null) {

            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(getOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient().newBuilder();

        //for slower connections, try for 20 secs and then timeout
        builder.connectTimeout(60, TimeUnit.SECONDS);
        builder.readTimeout(60, TimeUnit.SECONDS);
        builder.writeTimeout(60, TimeUnit.SECONDS);
        builder.cache(getCache());

        builder.addInterceptor(new NetworkInterceptor() {
            @Override
            public boolean isInternetAvailable() {
                return NetworkUtil.isOnline(context);
            }

            @Override
            public void onInternetUnavailable() {
                if(listener != null)
                    listener.onInternetUnavailable();

            }

            @Override
            public void onCacheUnavailable() {
                if(listener != null)
                    listener.onCacheUnavailable();
            }
        });

        return builder.build();
    }

    public static Cache getCache() {
        File cacheDir = new File(context.getCacheDir(), "cache");
        Cache cache = new Cache(cacheDir, DISK_CACHE_SIZE);
        return cache;
    }
}

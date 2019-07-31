package mfp.com.nytimesnewssearch.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by nivesuresh on 7/27/19.
 */

public abstract class NetworkInterceptor implements Interceptor {
    public abstract boolean isInternetAvailable();

    public abstract void onInternetUnavailable();

    public abstract void onCacheUnavailable();

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if (!isInternetAvailable()) {
            onInternetUnavailable();

            //allowing to display the response that's cached within the past 24 hours
            request = request.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + 60*60*24)
                    .build();

            Response response = chain.proceed(request);

            if(response.cacheResponse() == null) {
                onCacheUnavailable();
            }
            return response;
        }
        return chain.proceed(request);
    }
}

package mfp.com.nytimesnewssearch.network;

/**
 * Created by nivesuresh on 7/27/19.
 */

public interface InternetListener {
    void onInternetUnavailable();
    void onCacheUnavailable();
}

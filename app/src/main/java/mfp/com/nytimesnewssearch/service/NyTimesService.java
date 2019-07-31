package mfp.com.nytimesnewssearch.service;

import io.reactivex.Observable;
import mfp.com.nytimesnewssearch.models.ResultsModel;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nivesuresh on 7/28/19.
 */

public interface NyTimesService {
    @GET("articlesearch.json?")
    Observable<ResultsModel> getNewsArticles(@Query("q") String query, @Query("api-key") String apiKey,
                                             @Query("page") int page);
}

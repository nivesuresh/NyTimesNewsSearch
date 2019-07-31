package mfp.com.nytimesnewssearch.mvp;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import mfp.com.nytimesnewssearch.R;
import mfp.com.nytimesnewssearch.models.DocsModel;
import mfp.com.nytimesnewssearch.models.MetaModel;
import mfp.com.nytimesnewssearch.models.ResultsModel;
import mfp.com.nytimesnewssearch.service.NyTimesService;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class NewsSearchPresenter {
    private NewsSearchView newsSearchView;
    private NyTimesService service;
    boolean hasMore = false;
    int page = 0;
    private Context context;
    private List<DocsModel> docsModel = new ArrayList<>();

    public NewsSearchPresenter(Context context, NewsSearchView newsSearchView, NyTimesService service) {
        this.newsSearchView = newsSearchView;
        this.service = service;
        this.context = context;
    }

    public void resetPage() {
        this.page = 0;
        this.hasMore = false;
        docsModel.clear();
    }

    public void getFirstPageResults(String query) {
        getObservable(query).subscribeWith(getObserver());
    }

    public void getNextResults(String query) {
        getObservable(query).subscribeWith(getNextObserver());
    }

    public Observable<ResultsModel> getObservable(String query){
        return service.getNewsArticles(query,
                context.getString(R.string.api_key),
                page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<ResultsModel> getObserver(){
        return new DisposableObserver<ResultsModel>() {

            @Override
            public void onNext(@NonNull ResultsModel resultsModel) {
                MetaModel meta = resultsModel.getResponseModel().getMeta();
                page = (meta.getOffset()/10) + 1;
                hasMore = meta.getHits() > meta.getOffset();
                docsModel.addAll(resultsModel.getResponseModel().getDocsList());
                newsSearchView.updateResults(docsModel);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
    }

    /**
     * Pretty similar method to getObserver(), but instead of calling for the
     * method that sets the adapter, we're just notifying the adapter with
     * the newest additions
     */
    public DisposableObserver<ResultsModel> getNextObserver(){
        return new DisposableObserver<ResultsModel>() {

            @Override
            public void onNext(@NonNull ResultsModel resultsModel) {
                MetaModel meta = resultsModel.getResponseModel().getMeta();
                page = (meta.getOffset()/10) + 1;
                hasMore = meta.getHits() > meta.getOffset();
                docsModel.addAll(resultsModel.getResponseModel().getDocsList());
                newsSearchView.addToResults();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        };
    }
}

package mfp.com.nytimesnewssearch.mvp;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.SearchView;

import java.util.List;

import mfp.com.nytimesnewssearch.R;
import mfp.com.nytimesnewssearch.models.DocsModel;
import mfp.com.nytimesnewssearch.network.InternetListener;
import mfp.com.nytimesnewssearch.network.NetworkUtil;
import mfp.com.nytimesnewssearch.service.NyTimesService;
import mfp.com.nytimesnewssearch.service.RetrofitClientInstance;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class NewsSearchActivity extends AppCompatActivity implements NewsSearchView, InternetListener {

    private NewsSearchAdapter adapter;
    private NyTimesService service;
    private RecyclerView cardView;
    private boolean loading = true;
    private int previousTotal = 0;
    private NewsSearchPresenter presenter;

    private String searchText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_search);

        service = RetrofitClientInstance.getRetrofitInstance(this).create(NyTimesService.class);
        presenter = new NewsSearchPresenter(this, this, service);
        cardView = findViewById(R.id.card_view);
        searchText = "";

        final LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardView.setLayoutManager(llm);

        handleIntent(getIntent());

        cardView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = llm.getItemCount();
                int visibleItemCount = recyclerView.getChildCount();
                int firstVisibleItemPosition = llm.findFirstVisibleItemPosition();

                if(loading) {
                    if(totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if(!loading
                        && NetworkUtil.isOnline(NewsSearchActivity.this)
                        && presenter.hasMore
                        && visibleItemCount + firstVisibleItemPosition >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    presenter.getNextResults(searchText);
                    loading = true;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        RetrofitClientInstance.removeInternetListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        RetrofitClientInstance.setInternetListener(this);

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            if(!query.equals(searchText)) {
                presenter.resetPage();
                searchText = query;
                previousTotal = 0;
                loading = true;
            }

            presenter.getFirstPageResults(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the options menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default

        return true;
    }

    private void generateDataList(List<DocsModel> resultsList) {
        adapter = new NewsSearchAdapter(this, resultsList);
        cardView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateResults(List<DocsModel> docsModelList) {
        generateDataList(docsModelList);
    }

    /**
     * This method is so that we don't set the adapter everytime we paginate.
     * That takes us back to the top every time we load more data from server.
     */
    @Override
    public void addToResults() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onInternetUnavailable() {
        View view = findViewById(R.id.constraint_layout);
        Snackbar.make(view, R.string.no_internet, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onCacheUnavailable() {
        View view = findViewById(R.id.constraint_layout);
        Snackbar.make(view, R.string.no_content_available, Snackbar.LENGTH_SHORT).show();
    }
}
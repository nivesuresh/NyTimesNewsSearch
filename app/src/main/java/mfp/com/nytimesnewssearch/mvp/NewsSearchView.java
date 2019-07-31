package mfp.com.nytimesnewssearch.mvp;

import java.util.List;

import mfp.com.nytimesnewssearch.models.DocsModel;

/**
 * Created by nivesuresh on 7/28/19.
 */

public interface NewsSearchView {
    void updateResults(List<DocsModel> docsModelList);
    void addToResults();
}

package mfp.com.nytimesnewssearch.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class ResponseModel {
    @SerializedName("docs")
    List<DocsModel> docsList;

    @SerializedName("meta")
    MetaModel meta;

    public List<DocsModel> getDocsList() {
        return docsList;
    }

    public void setDocsList(List<DocsModel> docsList) {
        this.docsList = docsList;
    }

    public MetaModel getMeta() {
        return meta;
    }

    public void setMeta(MetaModel meta) {
        this.meta = meta;
    }
}

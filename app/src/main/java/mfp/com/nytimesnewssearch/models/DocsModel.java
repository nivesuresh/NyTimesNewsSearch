package mfp.com.nytimesnewssearch.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class DocsModel {

    @SerializedName("web_url")
    String web_url;

    @SerializedName("lead_paragraph")
    String lead_paragraph;

    @SerializedName("multimedia")
    List<MultimediaModel> multimediaModelList;

    @SerializedName("headline")
    HeadlineModel headline;

    public String getWeb_url() {
        return web_url;
    }

    public void setWeb_url(String web_url) {
        this.web_url = web_url;
    }

    public String getLead_paragraph() {
        return lead_paragraph;
    }

    public void setLead_paragraph(String lead_paragraph) {
        this.lead_paragraph = lead_paragraph;
    }

    public List<MultimediaModel> getMultimediaModelList() {
        return multimediaModelList;
    }

    public void setMultimediaModelList(List<MultimediaModel> multimediaModelList) {
        this.multimediaModelList = multimediaModelList;
    }

    public HeadlineModel getHeadline() {
        return headline;
    }

    public void setHeadline(HeadlineModel headline) {
        this.headline = headline;
    }
}

package mfp.com.nytimesnewssearch.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class ResultsModel {
    @SerializedName("status")
    String status;

    @SerializedName("copyright")
    String copyright;

    @SerializedName("response")
    ResponseModel responseModel;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public ResponseModel getResponseModel() {
        return responseModel;
    }

    public void setResponseModel(ResponseModel responseModel) {
        this.responseModel = responseModel;
    }
}

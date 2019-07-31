package mfp.com.nytimesnewssearch.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class MultimediaModel {

    @SerializedName("url")
    String url;

    @SerializedName("height")
    int height;

    @SerializedName("width")
    int width;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

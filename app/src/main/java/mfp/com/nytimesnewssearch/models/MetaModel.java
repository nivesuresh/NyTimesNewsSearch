package mfp.com.nytimesnewssearch.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class MetaModel {
    @SerializedName("hits")
    int hits;

    @SerializedName("offset")
    int offset;

    @SerializedName("time")
    int time;

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}

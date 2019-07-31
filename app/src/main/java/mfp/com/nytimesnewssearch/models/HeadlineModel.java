package mfp.com.nytimesnewssearch.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nivesuresh on 7/28/19.
 */

public class HeadlineModel {
    @SerializedName("main")
    String main;

    @SerializedName("print_headline")
    String print_headline;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getPrint_headline() {
        return print_headline;
    }

    public void setPrint_headline(String print_headline) {
        this.print_headline = print_headline;
    }
}

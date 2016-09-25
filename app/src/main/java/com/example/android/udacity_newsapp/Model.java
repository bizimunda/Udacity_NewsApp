package com.example.android.udacity_newsapp;

/**
 * Created by temp on 25/09/2016.
 */
public class Model {



    private String webTitle;
    private String authors;
    private String mUrl;

    public Model(String webTitle, String authors, String mUrl) {
        this.webTitle = webTitle;
        this.authors = authors;
        this.mUrl=mUrl;

    }

    public Model(){

    }

    public String getWebTitle() {
        return webTitle;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}

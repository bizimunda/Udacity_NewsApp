package com.example.android.udacity_newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

/**
 * Created by temp on 25/09/2016.
 */
public class LoaderModel extends AsyncTaskLoader<List<Model>> {

    /** Tag for log messages */
    private static final String LOG_TAG = LoaderModel.class.getName();

    /** Query URL */
    private String mUrl;


    public LoaderModel(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Model> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        Log.i("loadInBackground", "Loading in back ground");

        // Perform the network request, parse the response, and extract a list of earthquakes.
        List<Model> modelList = QueryUtils.fetchEarthquakeData(mUrl);
        return modelList;
    }
}

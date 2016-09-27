package com.example.android.udacity_newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<List<Model>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String GUARDIAN_REQUEST_URL ="http://content.guardianapis.com/search?q=pokemon&api-key=test&show-tags=contributor";
    //14a7d8f2-5533-457e-a012-1d0b674582e8

    private static final int APPNEWS_LOADER_ID = 1;
    private ModelAdapter mAdapter;
    private ListView modelListView;
    private LoaderManager loaderManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("Loader", "onCrete Activity method");

        modelListView = (ListView) findViewById(R.id.list);
        mAdapter = new ModelAdapter(MainActivity.this, new ArrayList<Model>());
        modelListView.setAdapter(mAdapter);
        Log.i("Loader", "Entering updateListView method");

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            updateListview();

        } else {
            Toast.makeText(MainActivity.this,"No data connection",Toast.LENGTH_SHORT).show();
        }

        modelListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Model currentEarthquake = mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getmUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

    }

    private void updateListview(){

        loaderManager = getLoaderManager();
        loaderManager.initLoader(APPNEWS_LOADER_ID, null, MainActivity.this);
    }

    @Override
    public Loader<List<Model>> onCreateLoader(int id, Bundle args) {
        Log.i("Loader", "onCreateLoader");
        return new LoaderModel(this, GUARDIAN_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Model>> loader, List<Model> data) {
        Log.i("Loader", "onLoad finished");
        mAdapter.clear();

        String k= loader.dataToString(data);
        Log.i("Loader", k);

        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Model>> loader) {
        mAdapter.clear();

    }

}

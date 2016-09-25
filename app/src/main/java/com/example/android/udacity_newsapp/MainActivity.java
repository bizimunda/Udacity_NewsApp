package com.example.android.udacity_newsapp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements LoaderManager.LoaderCallbacks<List<Model>> {

    private static final String LOG_TAG = MainActivity.class.getName();

    private static final String USGS_REQUEST_URL =
            "http://content.guardianapis.com/search?q=java&api-key=14a7d8f2-5533-457e-a012-1d0b674582e8";


    private static final int EARTHQUAKE_LOADER_ID = 1;
    private ModelAdapter mAdapter;
    private EditText etSearch;
    private Button btnSearch;
    private ListView modelListView;
    private LoaderManager loaderManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modelListView = (ListView) findViewById(R.id.list);
        etSearch= (EditText) findViewById(R.id.et_main_search);
        btnSearch= (Button) findViewById(R.id.btn_main_search);

       btnSearch.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mAdapter = new ModelAdapter(MainActivity.this, new ArrayList<Model>());
               modelListView.setAdapter(mAdapter);

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

               loaderManager = getLoaderManager();

               loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, MainActivity.this);
           }
       });

    }

    @Override
    public Loader<List<Model>> onCreateLoader(int id, Bundle args) {

        return new LoaderModel(this, USGS_REQUEST_URL);

    }

    @Override
    public void onLoadFinished(Loader<List<Model>> loader, List<Model> data) {
        Log.i("onLoadFinish","onLoad Finished");
        mAdapter.clear();


        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }


    }

    @Override
    public void onLoaderReset(Loader<List<Model>> loader) {
        mAdapter.clear();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

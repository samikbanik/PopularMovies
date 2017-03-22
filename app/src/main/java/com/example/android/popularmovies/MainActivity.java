package com.example.android.popularmovies;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.example.android.popularmovies.utilities.MoviesJsonUtils;
import com.example.android.popularmovies.utilities.NetworkUtils;
import java.net.URL;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.android.popularmovies.MoviesAdapter.MoviesAdapterOnClickHandler;

public class MainActivity extends AppCompatActivity implements MoviesAdapterOnClickHandler {

    private static String sort_by="";
    private RecyclerView mRecyclerView;
    private MoviesAdapter mMoviesAdapter;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This RecyclerView is used to display all the movie posters
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        // This TextView is used to display errors and will be hidden if there are no errors //
        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        //GridLayoutManager to display the movie posters in a grid format
        GridLayoutManager layoutManager;
        Configuration config = getResources().getConfiguration();
        int orientation = config.orientation;
        if(orientation == Configuration.ORIENTATION_PORTRAIT){
            layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        } else {
            layoutManager = new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        }

        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMoviesAdapter = new MoviesAdapter(this);

        mRecyclerView.setAdapter(mMoviesAdapter);

        /*
         * The ProgressBar that will indicate to the user that we are loading data. It will be
         * hidden when no data is loading.
         */

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMovieData();
    }

    private void loadMovieData() {
        showMovieDataView();
        new FetchMovieTask().execute();
    }

    @Override
    public void onClick(Movies selectedMovie) {
        Context context = this;
        Class destinationClass = DetailActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra("movieObject", selectedMovie);
        startActivity(intentToStartDetailActivity);
    }

    /*
     * This method will make the View for the movie posters visible and
     * hide the error message.
     */
    private void showMovieDataView() {

        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    /*
     * This method will make the error message visible and hide the RecyclerView
     */
    private void showErrorMessage() {

        mRecyclerView.setVisibility(View.INVISIBLE);

        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, Movies[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movies[] doInBackground(String... params) {

            URL weatherRequestUrl = NetworkUtils.buildUrl(sort_by);

            try {
                String jsonMovieResponse = NetworkUtils
                        .getResponseFromHttpUrl(weatherRequestUrl);

                Movies[] simpleJsonMovieData = MoviesJsonUtils
                        .getMoviePostersFromJson(MainActivity.this, jsonMovieResponse);

                return simpleJsonMovieData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Movies[] movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (movieData != null) {
                showMovieDataView();
                mMoviesAdapter.setMovieData(movieData);
            } else {
                showErrorMessage();
            }
        }
    }

    //Inflating the menu to sort the movies
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);
        return true;
    }

    //Handling the selected menu item for sorting
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sort_by_popularity) {
            sort_by = "popular";
            loadMovieData();
            return true;
        }
        if (id == R.id.sort_by_rating) {
            sort_by = "top_rated";
            loadMovieData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
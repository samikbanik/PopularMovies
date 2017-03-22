package com.example.android.popularmovies.utilities;

/**
 * Created by SamikBanik on 3/10/2017.
 */

import android.content.ContentValues;
import android.content.Context;

import com.example.android.popularmovies.Movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public final class MoviesJsonUtils {

    public static String POSTER_BASE_URL = "http://image.tmdb.org/t/p/w185";

    public static Movies[] getMoviePostersFromJson(Context context, String jsonMovies)
            throws JSONException {

        final String RESULTS = "results";
        final String OWM_MESSAGE_CODE = "cod";
        String poster = null;
        String title = null;
        String release_date = null;
        String rating = null;
        String overview = null;

        JSONObject movies = new JSONObject(jsonMovies);

        // If there there is an error
        if (movies.has(OWM_MESSAGE_CODE)) {
            int errorCode = movies.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    return null;
                default:
                    return null;
            }
        }

        JSONArray moviesArray = movies.getJSONArray(RESULTS);
        Movies[] movieObjects = new Movies[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {

            String posterPath;
            JSONObject movie = moviesArray.getJSONObject(i);
            posterPath = movie.getString("poster_path");
            title = movie.getString("original_title");
            release_date = movie.getString ("release_date");
            rating = movie.getString("vote_average");
            poster = POSTER_BASE_URL + posterPath;
            overview = movie.getString("overview");
            movieObjects[i] = new Movies(title, release_date, rating, poster, overview);
        }

        return movieObjects;
    }

}
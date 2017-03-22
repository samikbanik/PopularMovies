package com.example.android.popularmovies.utilities;

/**
 * Created by SamikBanik on 3/10/2017.
 */

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    final static String MOVIES_BASE_URL="http://api.themoviedb.org/3/";
    final static String API_KEY_PARAM = "api_key";
    final static String API_KEY = [YOUR_API_KEY]; //API_KEY to be placed here

    public static URL buildUrl(String sort_by) {

        Uri builtUri;
        if(sort_by == ""){
            builtUri = Uri.parse(MOVIES_BASE_URL + "discover/movie").buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .build();
        }else{
            builtUri = Uri.parse(MOVIES_BASE_URL + "movie/" + sort_by).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .build();
        }

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}
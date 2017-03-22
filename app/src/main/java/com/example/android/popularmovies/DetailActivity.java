package com.example.android.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private Movies mMovie;
    private TextView mTitle;
    private TextView mRelease;
    private TextView mRating;
    private ImageView mPoster;
    private TextView mOverview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTitle = (TextView) findViewById(R.id.title);
        mRelease = (TextView) findViewById(R.id.release_date);
        mRating = (TextView) findViewById(R.id.rating);
        mPoster = (ImageView) findViewById(R.id.poster_detail);
        mOverview = (TextView) findViewById(R.id.overview);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra("movieObject")) {
                mMovie = intentThatStartedThisActivity.getExtras().getParcelable("movieObject");
                mTitle.setText(mMovie.title);
                mRelease.setText(getMonth(mMovie.release_date) + "\n" + mMovie.release_date.substring(0,4));
                mRating.setText(mMovie.rating + "/10");
                Picasso.with(this).load(mMovie.poster_path).into(mPoster);
                mOverview.setText(mMovie.overview);
            }
        }
    }

    public String getMonth(String date){
        String months[] = new String[] {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String month;
        int month_index = Integer.parseInt(date.substring(5,7));
        switch(month_index){
            case 1: month = months[0];
                break;
            case 2: month = months[1];
                break;
            case 3: month = months[2];
                break;
            case 4: month = months[3];
                break;
            case 5: month = months[4];
                break;
            case 6: month = months[5];
                break;
            case 7: month = months[6];
                break;
            case 8: month = months[7];
                break;
            case 9: month = months[8];
                break;
            case 10: month = months[9];
                break;
            case 11: month = months[10];
                break;
            case 12: month = months[11];
                break;
            default: month = "Month not known !";
        }
        return month;
    }

}
package com.example.android.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SamikBanik on 3/12/2017.
 */

public class Movies implements Parcelable{
    String title;
    String release_date;
    String rating;
    String poster_path;
    String overview;
    public Movies(String title, String release_date, String rating, String poster_path, String overview){
        this.title = title;
        this.release_date = release_date;
        this.rating = rating;
        this.poster_path = poster_path;
        this.overview = overview;
    }

    public Movies(Parcel in){
        title = in.readString();
        release_date = in.readString();
        rating = in.readString();
        poster_path = in.readString();
        overview = in.readString();
    }

    @Override
    public int describeContents(){ return 0; }

    public String toString(){ return title + "--" + release_date + "--" + rating + "--" + poster_path + "--" + overview; }

    @Override
    public void writeToParcel(Parcel parcel, int i){
        parcel.writeString(title);
        parcel.writeString(release_date);
        parcel.writeString(rating);
        parcel.writeString(poster_path);
        parcel.writeString(overview);
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>(){
      @Override
        public Movies createFromParcel(Parcel parcel){ return new Movies(parcel);}

        @Override
        public Movies[] newArray(int i){ return new Movies[i]; }
    };
}

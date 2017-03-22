package com.example.android.popularmovies;

/**
 * Created by SamikBanik on 3/12/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesAdapterViewHolder> {

    private String[] mPosterData = new String[20];
    private Movies[] mMoviesData = new Movies[20];
    Context context;

    private final MoviesAdapterOnClickHandler mClickHandler;

    /*
     * The interface that receives onClick messages.
     */
    public interface MoviesAdapterOnClickHandler {
        void onClick(Movies selectedMovie);
    }

    public MoviesAdapter(MoviesAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    /**
     * Cache of the children views for a movie poster.
     */
    public class MoviesAdapterViewHolder extends RecyclerView.ViewHolder implements OnClickListener {
        public ImageView posterView;

        public MoviesAdapterViewHolder(View view) {
            super(view);
            posterView = (ImageView) view.findViewById(R.id.poster_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Movies selectedMovie = new Movies(mMoviesData[adapterPosition].title, mMoviesData[adapterPosition].release_date, mMoviesData[adapterPosition].rating, mMoviesData[adapterPosition].poster_path, mMoviesData[adapterPosition].overview);
            mClickHandler.onClick(selectedMovie);
        }
    }

    @Override
    public MoviesAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.poster_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        return new MoviesAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MoviesAdapterViewHolder movieAdapterViewHolder, int position) {
        String poster = mPosterData[position];
        Picasso.with(context).load(poster).into(movieAdapterViewHolder.posterView);
    }

    @Override
    public int getItemCount() {
        if (null == mPosterData) return 0;
        return mPosterData.length;
    }


    public void setMovieData(Movies[] movieData) {
        for(int i = 0; i < 20; i++){
            mMoviesData[i] = new Movies(movieData[i].title, movieData[i].release_date, movieData[i].rating, movieData[i].poster_path, movieData[i].overview);
            mPosterData[i] = movieData[i].poster_path;
        }
        notifyDataSetChanged();
    }
}
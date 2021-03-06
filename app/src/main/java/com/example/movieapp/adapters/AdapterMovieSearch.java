package com.example.movieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.movieapp.MovieInfo;
import com.example.movieapp.R;
import com.example.movieapp.VolleySingleton.VolleySingleton;
import com.example.movieapp.data.Movie;

import java.util.ArrayList;

/**
 * Created by Deepak Kumar on 7/9/2015.
 */

public class AdapterMovieSearch extends RecyclerView.Adapter<AdapterMovieSearch.SearchDataHolder> {
    LayoutInflater layoutInflator;
    public ArrayList<Movie> movieList;
    private ImageLoader imageLoader;
    VolleySingleton volleySingleton;


    public AdapterMovieSearch(){}
    public void setMovieList(ArrayList<Movie> movieListData) {
        this.movieList = movieListData;
        notifyItemRangeChanged(0, movieList.size());

    }


    public AdapterMovieSearch(Context context, ArrayList<Movie> movieListResp) {
        layoutInflator = LayoutInflater.from(context);
        setMovieList(movieListResp);
        imageLoader = volleySingleton.getInstance().getImageLoader();
    }

    public String printID(int position){
        return movieList.get(position).getId();
    }
    @Override
    public SearchDataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = layoutInflator.inflate(R.layout.layout_cell, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        SearchDataHolder viewHolder = new SearchDataHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final SearchDataHolder holder, int i) {
        holder.movieId.setText(movieList.get(i).getTitle());
        String urlThumbnail = movieList.get(i).getUrlThumbnail();
        imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                holder.moviePoster.setImageBitmap(response.getBitmap());
            }
        });
        //holder.moviePoster.set
        //Log.e("tag",movieList.get(i).getUrlThumbnail());
        holder.moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("tag", "image clicked"

                );
            }
        });
        String strRating=movieList.get(i).getRatings();
        Float rating=Float.valueOf(strRating)%10;
        holder.movieRating.setRating(rating);
    }

    @Override
    public int getItemCount() {
        Log.e("tag", "getItemCount");
        return movieList.size();
        //return 10;
    }



    public class SearchDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView moviePoster;
        public TextView movieId;
        public Layout layout;
        public Context context;
        public RatingBar movieRating;
        public SearchDataHolder(View itemView) {
            super(itemView);
            context=itemView.getContext();
            Log.e("tag", "SearchDataHolder");
            movieRating= (RatingBar) itemView.findViewById(R.id.rtbHighScore);
            movieId = (TextView) itemView.findViewById(R.id.firstLine);
            moviePoster = (ImageView) itemView.findViewById(R.id.icon);

            YoYo.with(Techniques.FlipInX)
                    .duration(600)
                    .playOn(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int i=getPosition();
            String v_ID=printID(getPosition());
            final Intent myIntent=new Intent();
            if(context!=null) {
                myIntent.setClass(context, MovieInfo.class);
                myIntent.putExtra("movieID", v_ID);
                context.startActivity(myIntent);
            }
        }
    }

}
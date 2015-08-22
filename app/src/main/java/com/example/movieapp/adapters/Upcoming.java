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

public class Upcoming extends RecyclerView.Adapter<Upcoming.UpcomingDataHolder> {
    LayoutInflater layoutInflator;
    public ArrayList<Movie> movieList;
    private ImageLoader imageLoader;
    VolleySingleton volleySingleton;


    public void setMovieList(ArrayList<Movie> movieListData) {
        Log.e("tag", "SetMovieList");
        this.movieList = movieListData;
        Log.e("tag", "Constructor" + movieList.toString());
        notifyItemRangeChanged(0, movieList.size());

    }


    public Upcoming(Context context, ArrayList<Movie> movieListResp) {
        layoutInflator = LayoutInflater.from(context);
        Log.e("tag", "Constructor" + movieListResp.toString());
        setMovieList(movieListResp);
        //notifyItemRangeChanged(0, movieList.size());
        imageLoader = volleySingleton.getInstance().getImageLoader();
    }

    public String printID(int position){
        Log.e("tagID2",Integer.toString(position));
        return movieList.get(1).getId();
    }

    @Override
    public UpcomingDataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //ViewGroup parent
        Log.e("tag", "SearchDataHolder");
        View view = layoutInflator.inflate(R.layout.layout_cell, viewGroup, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        UpcomingDataHolder viewHolder = new UpcomingDataHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final UpcomingDataHolder holder, int i) {
        //Movie currentMovie=movieList.get(i);
        Log.e("tag", "onBindViewHolder");
        holder.movieId.setText(movieList.get(i).getTitle());
        Log.e("tag2", Integer.toString(movieList.size()));
        String urlThumbnail = movieList.get(i).getUrlThumbnail();
        Log.e("tag", "urlThumbnail" + urlThumbnail);
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
    }

    @Override
    public int getItemCount() {
        Log.e("tag", "getItemCount");
        return movieList.size();
        //return 10;
    }



    public class UpcomingDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView moviePoster;
        public TextView movieId;
        public Layout layout;
        public Context context;
        public RatingBar rating;
        public UpcomingDataHolder(View itemView) {
            super(itemView);
            context=itemView.getContext();
            Log.e("tag", "SearchDataHolder");
            // moviePoster= (ImageView) itemView.findViewById(R.id.icon);
            rating= (RatingBar) itemView.findViewById(R.id.rtbHighScore);
            movieId = (TextView) itemView.findViewById(R.id.firstLine);
            moviePoster = (ImageView) itemView.findViewById(R.id.icon);

            YoYo.with(Techniques.FlipInX)
                    .duration(600)
                    .playOn(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.e("tag", "onclick called");
            int i=getPosition();
            Log.e("tag", movieList.get(getPosition()).getUrlThumbnail());
            Log.e("tag12", getPosition() + " pos " + printID(getPosition()));
            String v_ID=printID(getPosition());
            final Intent myIntent=new Intent();
            if(context!=null) {
                myIntent.setClass(context, MovieInfo.class);
                myIntent.putExtra("movieID", v_ID);
                Log.e("tagAdapter",v_ID.toString());
                context.startActivity(myIntent);
            }
           /* Intent myIntent = new Intent(, MovieInfo.class);
            CurrentActivity.this.startActivity(myIntent);*/
        }
    }

}
package com.example.movieapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.movieapp.R;
import com.example.movieapp.data.Movie;


import java.util.ArrayList;

/**
 * Created by Deepak Kumar on 7/9/2015.
 */

public class AdapterMovieSearch extends RecyclerView.Adapter<AdapterMovieSearch.SearchDataHolder>{
    LayoutInflater layoutInflator;
    public ArrayList<Movie> movieList;

    public void setMovieList(ArrayList<Movie> movieListData){
        Log.e("tag","SetMovieList");
        this.movieList=movieListData;
        Log.e("tag","Constructor"+movieList.toString());
        notifyItemRangeChanged(0, movieList.size());
    }


    public AdapterMovieSearch(Context context,ArrayList<Movie> movieListResp)
    {
        layoutInflator=LayoutInflater.from(context);
        Log.e("tag", "Constructor" + movieListResp.toString());
        setMovieList(movieListResp);
        //notifyItemRangeChanged(0, movieList.size());

        }
    @Override
    public SearchDataHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //ViewGroup parent
        Log.e("tag","SearchDataHolder");
        View view=layoutInflator.inflate(R.layout.layout_cell, viewGroup, false);
        SearchDataHolder viewHolder=new SearchDataHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchDataHolder holder, int i) {
        //Movie currentMovie=movieList.get(i);
        Log.e("tag","onBindViewHolder");
        holder.movieId.setText(movieList.get(i).getTitle());
        Log.e("tag2", Integer.toString(movieList.size()));
        //Log.e("tag",movieList.get(i).getUrlThumbnail());
    }

    @Override
    public int getItemCount() {
        Log.e("tag","getItemCount");
        return movieList.size();
        //return 10;
    }

    public class SearchDataHolder extends RecyclerView.ViewHolder{

        public ImageView moviePoster;
        public TextView movieId;

        public SearchDataHolder(View itemView) {
            super(itemView);
            Log.e("tag", "SearchDataHolder");
           // moviePoster= (ImageView) itemView.findViewById(R.id.icon);
            movieId= (TextView) itemView.findViewById(R.id.firstLine);
        }
    }

}
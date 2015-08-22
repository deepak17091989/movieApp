package com.example.movieapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.movieapp.R;
import com.example.movieapp.VolleySingleton.VolleySingleton;
import com.example.movieapp.adapters.AdapterMovieSearch;
import com.example.movieapp.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
/**
 * Created by Edwin on 15/02/2015.
 */
public class Tab1 extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout swipeRefresh;
    VolleySingleton volleySingleton;
    LinearLayout layout;
    RecyclerView mRecyclerView;
    SearchView searchView;
    RequestQueue requestQ;
    AdapterMovieSearch movieAdapter;
    ArrayList<Movie> response2;
    public ArrayList<Movie> movieList = new ArrayList<>();
    JSONrequest JsonRequest;
    RatingBar rating;
    public String searchQuery="null";
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_1, container, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        movieAdapter = new AdapterMovieSearch(getActivity(),movieList);
        fab= (FloatingActionButton) v.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Fab Clicked",Toast.LENGTH_LONG).show();
            }
        });
        layout = (LinearLayout) v.findViewById(R.id.progresslayout);
        layout.setVisibility(View.GONE);
        swipeRefresh= (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLayout);
        swipeRefresh.setOnRefreshListener(this);
        searchView= (SearchView) v.findViewById(R.id.search_view);
        searchView.setQueryHint("Movie Search");
//        layout.setVisibility(View.GONE);
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub

                Log.e("tag", String.valueOf(hasFocus));
            }
        });

        //*** setOnQueryTextListener ***
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO Auto-generated method stub
                searchQuery = query;
                Log.e("tagqueryonsubmit", query+searchQuery);
                mRecyclerView.removeAllViews();
                movieList.clear();
                //	Toast.makeText(getBaseContext(), newText,
                Log.e("tagquery", query);
                layout.setVisibility(View.VISIBLE);
                new JSONrequest().execute();
                layout.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO Auto-generated method stub
                return false;
            }
        });
        Log.e("tag",searchQuery);
        //searchPhrase.get
        Log.e("tag", "after execution" + movieList);
          LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
          mRecyclerView.setLayoutManager(layoutManager);
          mRecyclerView.setAdapter(movieAdapter);
        return v;
    }

    @Override
    public void onRefresh() {
        if(swipeRefresh.isRefreshing()) {
            swipeRefresh.setRefreshing(false);
        }
        mRecyclerView.setAdapter(movieAdapter);
    }

    class JSONrequest extends AsyncTask<Void, Void, Void> {
        private ArrayList<Movie> parseRequest(JSONObject response) {
            StringBuilder data = new StringBuilder();
            if (response == null || response.length() == 0)
                return null;
            if (response.has("movies")) {
                JSONArray moviesArray = null;
                try {
                    moviesArray = response.getJSONArray("movies");
                    Log.e("tag", "movies" + moviesArray);
                    for (int num = 0; num < moviesArray.length(); num++) {
                        JSONObject currentMovie = moviesArray.getJSONObject(num);
                        String v_ID=currentMovie.getString("id");
                        data.append("id="+v_ID+"\n");
                        String v_title = currentMovie.getString("title");
                        data.append("title=" + v_title + "\n");
                        JSONObject posterObject = currentMovie.getJSONObject("posters");
                        String v_poster = posterObject.getString("thumbnail");
                        data.append("poster=" + v_poster + "\n");
                        JSONObject ratingObject=currentMovie.getJSONObject("ratings");
                        String v_rating=ratingObject.getString("audience_score");
                        Log.e("rating",v_rating);
                        Movie movie = new Movie();
                        //movie.setId(Long.valueOf(v_ID).longValue());
                        movie.setTitle(v_title);
                        movie.setUrlThumbnail(v_poster);
                        movie.setId(v_ID);
                        movie.setRatings(v_rating);
                        movieList.add(movie);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return movieList;
        }

        @Override
        protected Void doInBackground(Void... params) {
            //layout.setVisibility(View.VISIBLE);
            //movieAdapter = new AdapterMovieSearch(getActivity());
            RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
            Log.e("tag",searchQuery);
            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://api.rottentomatoes.com/api/public/v1.0/movies.json?apikey=54wzfswsa4qmjg8hjwa64d4c&q="+searchQuery+"&page_limit=10", "NULL", new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    //Toast.makeText(getActivity(), "RESPONSE" + response, Toast.LENGTH_LONG).show();
                    Log.e("tag", "request");
                    movieAdapter.setMovieList(parseRequest(response));
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getActivity(), "Error" + error, Toast.LENGTH_LONG).show();
                }
            });
            requestQueue.add(request);

            try {
                Thread.sleep(1500);
                Log.e("tag", "tag");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.e("tag", "movies.toString()");
           // layout.setVisibility(View.GONE);

        }
    }
}
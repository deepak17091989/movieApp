package com.example.movieapp.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

    public class NewRelease extends Fragment {
        VolleySingleton volleySingleton;
        LinearLayout layout;
        RecyclerView mRecyclerView;
        RequestQueue requestQ;
        AdapterMovieSearch movieAdapter;
        ArrayList<Movie> response2;
        public ArrayList<Movie> movieList = new ArrayList<>();
        JSONrequest JsonRequest;

        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.boxofclayout, container, false);
            mRecyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
            movieAdapter = new AdapterMovieSearch(getActivity(),movieList);
            layout = (LinearLayout) v.findViewById(R.id.progressbar_view);
            new JSONrequest().execute();
            Log.e("tag", "after execution" + movieList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            mRecyclerView.setLayoutManager(layoutManager);
            mRecyclerView.setAdapter(movieAdapter);
            return v;
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
                            String v_title = currentMovie.getString("title");
                            data.append("title=" + v_title + "\n");
                            JSONObject movieObject = currentMovie.getJSONObject("posters");
                            String v_poster = movieObject.getString("thumbnail");
                            data.append("poster=" + v_poster + "\n");
                            Movie movie = new Movie();
                            //movie.setId(Long.valueOf(v_ID).longValue());
                            movie.setTitle(v_title);
                            movie.setUrlThumbnail(v_poster);
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
                layout.setVisibility(View.VISIBLE);
                //movieAdapter = new AdapterMovieSearch(getActivity());
                RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
                final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey=54wzfswsa4qmjg8hjwa64d4c&limit=50", "NULL", new Response.Listener<JSONObject>() {
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
                layout.setVisibility(View.GONE);

            }
        }
    }
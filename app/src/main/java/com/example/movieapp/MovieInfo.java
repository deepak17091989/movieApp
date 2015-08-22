package com.example.movieapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.movieapp.VolleySingleton.VolleySingleton;
import com.example.movieapp.data.Movie;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Deepak Kumar on 7/20/2015.
 */
public class MovieInfo extends Activity {
    public Movie movieObject=new Movie();
    VolleySingleton volleySingleton;
    RatingBar movieRating;
    ImageView v_poster;
    ImageLoader imageLoader;
    TextView v_Title;
    RatingBar v_Rating;
    TextView v_Genre;
    TextView v_Genre_List;
    TextView v_IMDB;
    TextView v_Plot;
    String movieID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_layout);
        movieID=getIntent().getExtras().getString("movieID");
        if(movieID!=null)
        {
            Log.e("tagID","the movie id is "+movieID);
        }
        v_poster= (ImageView) findViewById(R.id.imageMovie);
        v_Title= (TextView) findViewById(R.id.fieldTitle);
        v_Rating= (RatingBar) findViewById(R.id.rtbHighScore);
        v_Genre= (TextView) findViewById(R.id.labelGenres);
        v_Genre_List= (TextView) findViewById(R.id.fieldGenres);
        v_IMDB= (TextView) findViewById(R.id.labelImdb);
        v_Plot= (TextView) findViewById(R.id.fieldPlot);
        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://api.rottentomatoes.com/api/public/v1.0/movies/"+movieID+".json?apikey=54wzfswsa4qmjg8hjwa64d4c", "NULL", new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response", response.toString());
                try {
                    movieObject=parseRequest(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(request);
        movieRating= (RatingBar) findViewById(R.id.rtbHighScore);
    }

        private Movie parseRequest(JSONObject response) throws JSONException {
            StringBuilder data = new StringBuilder();
            if (response == null || response.length() == 0)
                Log.e("tag","null");
            movieObject.setId(response.getString("id"));
            JSONObject URLObject=response.getJSONObject("posters");
            movieObject.setUrlThumbnail(URLObject.getString("thumbnail"));
            movieObject.setTitle(response.getString("title"));
            movieObject.setSynopsis(response.getString("synopsis"));
            v_Title.setText(movieObject.getTitle());
            imageLoader = volleySingleton.getInstance().getImageLoader();
            imageLoader.get(movieObject.getUrlThumbnail(), new ImageLoader.ImageListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }

                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    v_poster.setImageBitmap(response.getBitmap());
                }
            });
            v_Plot.setText(movieObject.getSynopsis());
            JSONObject ratingObject=response.getJSONObject("ratings");
            String strRating=ratingObject.getString("audience_score");
            movieObject.setRatings(strRating);
            Float rating=Float.valueOf(movieObject.getRatings())%10;
            movieRating.setRating(rating);
            return movieObject;
        }

    }
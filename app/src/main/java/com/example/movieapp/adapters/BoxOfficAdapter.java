package com.example.movieapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.movieapp.R;
import com.example.movieapp.VolleySingleton.VolleySingleton;
import com.example.movieapp.data.Movie;

import java.util.ArrayList;

/**
 * Created by Deepak Kumar on 7/18/2015.
 */
public class BoxOfficAdapter extends RecyclerView.Adapter<BoxOfficAdapter.DisplayHolder> {
    LayoutInflater layoutInflator;
    public ArrayList<Movie> boxOfcList;
    private ImageLoader imageLoader;
    VolleySingleton volleySingleton;

    public void setMovieList(ArrayList<Movie> boxOfcListData) {
        Log.e("tag", "SetMovieList");
        this.boxOfcList = boxOfcListData;
        Log.e("tag", "Constructor" + boxOfcList.toString());
        notifyItemRangeChanged(0, boxOfcList.size());

    }


    public BoxOfficAdapter(Context context, ArrayList<Movie> boxOfcListData) {
        layoutInflator = LayoutInflater.from(context);
        Log.e("tag", "Constructor" + boxOfcListData.toString());
        setMovieList(boxOfcListData);
        //notifyItemRangeChanged(0, movieList.size());
        imageLoader = volleySingleton.getInstance().getImageLoader();
    }

    @Override
    public DisplayHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        //ViewGroup parent
        Log.e("tag", "SearchDataHolder");
        View view = layoutInflator.inflate(R.layout.boxofclayout, viewGroup, false);
        DisplayHolder viewHolder = new DisplayHolder(view);
        if(i%2==0)
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(final DisplayHolder holder, int i) {
        //Movie currentMovie=movieList.get(i);
        Log.e("tag", "onBindViewHolder");
        holder.movieId.setText(boxOfcList.get(i).getTitle());
        Log.e("tag2", Integer.toString(boxOfcList.size()));
        String urlThumbnail = boxOfcList.get(i).getUrlThumbnail();
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
    }

    @Override
    public int getItemCount() {
        Log.e("tag", "getItemCount");
        return boxOfcList.size();
    }

    public class DisplayHolder extends RecyclerView.ViewHolder{

        public ImageView moviePoster;
        public TextView movieId;

        public DisplayHolder(View itemView) {
            super(itemView);
            // moviePoster= (ImageView) itemView.findViewById(R.id.icon);
            movieId = (TextView) itemView.findViewById(R.id.firstLine);
            moviePoster = (ImageView) itemView.findViewById(R.id.icon);
            YoYo.with(Techniques.FlipInX)
                    .duration(400)
                    .playOn(itemView);

        }
    }

}

package com.example.movieapp.data;

/**
 * Created by Deepak Kumar on 7/18/2015.
 */
public class Misc {
    public String URL;
    private String apikey="apikey=54wzfswsa4qmjg8hjwa64d4c";
    public String returnURL(String data,Integer flag)
    {
     if(flag==0)
     {
         URL="http://api.rottentomatoes.com/api/public/v1.0/movies.json?"+apikey+"&q="+data+"&page_limit=10";

     }
        else if(flag==1)
     {
         URL="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/box_office.json?"+apikey+"&limit=50";
     }
        else if(flag==2)
     {
         URL="http://api.rottentomatoes.com/api/public/v1.0/lists/movies/upcoming.json?apikey="+apikey+"&page_limit=50";
     }
        else if(flag==3)
     {
        URL="http://api.rottentomatoes.com/api/public/v1.0/movies/"+data+".json?apikey=54wzfswsa4qmjg8hjwa64d4c";
     }
        return URL;
    }
}

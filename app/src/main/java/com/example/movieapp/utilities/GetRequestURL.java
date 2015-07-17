package com.example.movieapp.utilities;

/**
 * Created by Deepak Kumar on 7/6/2015.
 */
public class GetRequestURL {
    URLutils utils;
    public String getNewReleaseUrl(int limit){
        return utils.URL_UPCOMING
                + utils.URL_QMARK
                + utils.URL_KEY_VALUE
                + utils.CHAR_AMPERSAND
                + utils.URL_LIMIT
                + limit;
    }
    public String getTopOfTheChart(int limit){
        return utils.URL_UPCOMING
                + utils.URL_QMARK
                + utils.URL_KEY_VALUE
                + utils.CHAR_AMPERSAND
                + utils.URL_LIMIT
                + limit;

    }
    public String getSearchUrl(int limit)
    {
        return utils.URL_UPCOMING
                + utils.URL_QMARK
                + utils.URL_KEY_VALUE
                + utils.CHAR_AMPERSAND
                + utils.URL_LIMIT
                + limit;


    }
}

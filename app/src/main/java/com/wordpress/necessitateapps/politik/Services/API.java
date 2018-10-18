package com.wordpress.necessitateapps.politik.Services;

import retrofit2.Call;
import retrofit2.http.GET;


public interface API {
    String BASE_URL = "https://politik-api-heroku.herokuapp.com/";

    @GET(".")
    Call<ArticleList> getArticles();
}

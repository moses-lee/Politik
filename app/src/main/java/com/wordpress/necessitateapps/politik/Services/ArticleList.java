package com.wordpress.necessitateapps.politik.Services;

import com.google.gson.annotations.SerializedName;
import com.wordpress.necessitateapps.politik.Services.ArticleGetter;

import java.util.List;

public class ArticleList {

    public ArticleList(List<ArticleGetter> articles) {
        this.articles = articles;
    }

    @SerializedName("articles")

    private List<ArticleGetter> articles;

    public List<ArticleGetter> getArticles() {
        return articles;
    }


}

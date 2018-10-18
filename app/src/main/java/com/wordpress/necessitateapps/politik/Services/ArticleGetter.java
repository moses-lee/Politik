package com.wordpress.necessitateapps.politik.Services;

public class ArticleGetter {

    public ArticleGetter(){}

    private String title, summary, image, source, url, topic;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public ArticleGetter(String title, String summary, String image, String source, String url, String topic) {
        this.title = title;
        this.summary = summary;
        this.image = image;
        this.source = source;
        this.url = url;
        this.topic = topic;
    }
}

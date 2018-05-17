package com.org.news.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by babar on 5/17/2018.
 */

public class Article implements Parcelable {

    @SerializedName("source")
    @Expose
    public Source source;

    @SerializedName("author")
    @Expose
    public String author = "";

    @SerializedName("title")
    @Expose
    public String title= "";

    @SerializedName("description")
    @Expose
    public String description= "";

    @SerializedName("url")
    @Expose
    public String url= "";

    @SerializedName("urlToImage")
    @Expose
    public String urlToImage= "";

    @SerializedName("publishedAt")
    @Expose
    public String publishedAt= "";


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.source, flags);
        dest.writeString(this.author);
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.url);
        dest.writeString(this.urlToImage);
        dest.writeString(this.publishedAt);
    }

    public Article() {
    }

    protected Article(Parcel in) {
        this.source = in.readParcelable(Source.class.getClassLoader());
        this.author = in.readString();
        this.title = in.readString();
        this.description = in.readString();
        this.url = in.readString();
        this.urlToImage = in.readString();
        this.publishedAt = in.readString();
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel source) {
            return new Article(source);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}

package com.kier.tenorio.themovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kier on 07/02/2017.
 */
public class Movie implements Parcelable{



    private String imageUrl;
    private String originalTitle;
    private String plot;
    private double userRating;
    private String releaseDate;


    public Movie(String img,String title,String mPlot,double userRate,String date)
    {
        imageUrl=img;
        originalTitle=title;
        plot=mPlot;
        userRating=userRate;
        releaseDate=date;

    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getPlot() {
        return plot;
    }

    public double getUserRating() {
        return userRating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }


    @Override
    public String toString()
    {
        return String.format("Movie Title: %s Movie imgpath: %s",getOriginalTitle(),getImageUrl());
    }


    Movie(Parcel in)
    {
        this.imageUrl=in.readString();
        this.originalTitle=in.readString();
        this.plot=in.readString();
        this.userRating=in.readFloat();
        this.releaseDate=in.readString();
    }

    public void writeToParcel(Parcel dest,int flags)
    {
        dest.writeString(imageUrl);
        dest.writeString(originalTitle);
        dest.writeString(plot);
        dest.writeDouble(userRating);
        dest.writeString(releaseDate);
    }

    public int describeContents()
    {
        return 0;
    }


    static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>()
    {
        public Movie createFromParcel(Parcel in)
        {
            return new Movie(in);
        }

        public Movie[] newArray(int size)
        {
            return new Movie[size];
        }
    };
}

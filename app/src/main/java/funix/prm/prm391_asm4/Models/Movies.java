package funix.prm.prm391_asm4.Models;

import androidx.annotation.NonNull;

public class Movies {
    private String mImageLink;
    private String mMoviesName;
    private String mMoviePrice;

    public Movies() {
    }

    public Movies(String mImageLink, String mMoviesName, String mMoviePrice) {
        this.mImageLink = mImageLink;
        this.mMoviesName = mMoviesName;
        this.mMoviePrice = mMoviePrice;
    }

    public String getImageLink() {
        return mImageLink;
    }

    public void setImageLink(String mImageLink) {
        this.mImageLink = mImageLink;
    }

    public String getMoviesName() {
        return mMoviesName;
    }

    public void setMoviesName(String mMoviesName) {
        this.mMoviesName = mMoviesName;
    }

    public String getMoviePrice() {
        return mMoviePrice;
    }

    public void setMoviePrice(String mMoviePrice) {
        this.mMoviePrice = mMoviePrice;
    }

    @NonNull
    @Override
    public String toString() {
        return "ImageLink: " + mImageLink + "\n" +
                "Movies Name: " + mMoviesName + "\n" +
                "Movies Price: " + mMoviePrice + "\n";


    }
}

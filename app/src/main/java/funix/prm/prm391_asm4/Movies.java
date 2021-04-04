package funix.prm.prm391_asm4;

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

    public String getmImageLink() {
        return mImageLink;
    }

    public void setmImageLink(String mImageLink) {
        this.mImageLink = mImageLink;
    }

    public String getmMoviesName() {
        return mMoviesName;
    }

    public void setmMoviesName(String mMoviesName) {
        this.mMoviesName = mMoviesName;
    }

    public String getmMoviePrice() {
        return mMoviePrice;
    }

    public void setmMoviePrice(String mMoviePrice) {
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

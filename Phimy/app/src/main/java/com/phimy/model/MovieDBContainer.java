package com.phimy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieDBContainer {

    @SerializedName("results")
    private List<MovieDB> misMovies;

    public List<MovieDB> getMisMovies() {
        return misMovies;
    }
}

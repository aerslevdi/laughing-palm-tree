package com.phimy.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MovieDBContainer {
    // TODO Completar la clase
    @SerializedName("results")
    private List<MovieDB> misMovies;

    public List<MovieDB> getMisMovies() {
        return misMovies;
    }
}

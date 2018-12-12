package com.phimy.dao;

import android.graphics.Movie;
import android.telecom.Call;

import com.phimy.model.Cast;
import com.phimy.model.CastDBContainer;

import java.util.List;

import Utils.ResultListener;
import retrofit2.Callback;
import retrofit2.Response;

public class CastDBDAO extends DaoHelper{
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private ServiceMoviesDB serviceMoviesDB;
    private String api_key="5aa2e212bfa0373c59c3494bb068827f";

    public CastDBDAO() {
        super(BASE_URL);
        serviceMoviesDB = retrofit.create(ServiceMoviesDB.class);
    }


}

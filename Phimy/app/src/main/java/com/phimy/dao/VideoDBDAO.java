package com.phimy.dao;

public class VideoDBDAO extends DaoHelper {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private ServiceMoviesDB serviceMoviesDB;

    public VideoDBDAO() {
        super(BASE_URL);
       serviceMoviesDB = retrofit.create(ServiceMoviesDB.class);
    }
}

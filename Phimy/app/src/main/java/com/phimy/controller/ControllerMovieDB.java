package com.phimy.controller;

import android.content.Context;

import com.phimy.dao.MovieDBDao;
import com.phimy.model.Cast;
import com.phimy.model.MovieDB;
import com.phimy.model.VideoDB;

import java.util.List;

import Utils.InternetConnection;
import Utils.ResultListener;

public class ControllerMovieDB {
    final static public Integer KEY_SEARCH_POPULAR_MOVIE = 0;
    final static public Integer KEY_SEARCH_POPULAR_TV = 1;
    final static public Integer KEY_SEARCH_NOW_PLAYING = 2;

    MovieDBDao movieDBDao = new MovieDBDao();

    private static ControllerMovieDB instance;

    //public ControllerMovieDB() {
    //    this.movieDBDao = new MovieDBDao();
    //}

    public static ControllerMovieDB getInstance(){
        if (instance==null){
            instance= new ControllerMovieDB();
        }
        return instance;
    }

    public void getMovies(final ResultListener<List<MovieDB>> listenerView, Context context){
        if (InternetConnection.isConnection(context)) {
            movieDBDao.getMovies(new ResultListener<List<MovieDB>>() {
                @Override
                public void finish(List<MovieDB> resultado) {
                    listenerView.finish(resultado);
                }
            },KEY_SEARCH_POPULAR_MOVIE);
        }
    }

    public void getTvMovies(final ResultListener<List<MovieDB>> listenerView, Context context){
        if (InternetConnection.isConnection(context)) {
            movieDBDao.getMovies(new ResultListener<List<MovieDB>>() {
                @Override
                public void finish(List<MovieDB> resultado) {
                    listenerView.finish(resultado);
                }
            }, KEY_SEARCH_POPULAR_TV);
        }
    }

    public void getNowPlaying(final ResultListener<List<MovieDB>> listenerView, Context context){
        if (InternetConnection.isConnection(context)) {
            movieDBDao.getMovies(new ResultListener<List<MovieDB>>() {
                @Override
                public void finish(List<MovieDB> resultado) {
                    listenerView.finish(resultado);
                }
            }, KEY_SEARCH_NOW_PLAYING);
        }
    }

    public void getFavoritos(final ResultListener<List<MovieDB>> listenerView, Context context){
        if (InternetConnection.isConnection(context)) {
            movieDBDao.getFavoritos(new ResultListener<List<MovieDB>>() {
                @Override
                public void finish(List<MovieDB> resultado) {
                    listenerView.finish(resultado);
                }
            });
        }
    }

    // CONTROLLER GETCAST START
    public void getCast(Context context, final ResultListener<List<Cast>>viewListener, Integer movie_id){
        if (InternetConnection.isConnection(context)){
            movieDBDao.getCast(new ResultListener<List<Cast>>() {
                @Override
                public void finish(List<Cast> resultado) {
                    viewListener.finish(resultado);

                }
            }, movie_id);
        }
    }
    //CONTROLLER GETCAST END

    //CONTROLLER GETVIDEO START
    public void getVideoDB (Context context, final ResultListener<VideoDB> resultListener, Integer movie_id){
        if (InternetConnection.isConnection(context)){
            movieDBDao.getVideo(new ResultListener<VideoDB>() {
                @Override
                public void finish(VideoDB resultado) {
                    resultListener.finish(resultado);
                }
            }, movie_id);
        }
    }

    //CONTROLLER GETVIDEO END

    public void addFavoritos(MovieDB movieDB){
            movieDBDao.addFavoritos(movieDB);
    }

    public void removeFavoritos(MovieDB movieDB){
        movieDBDao.removeFavoritos(movieDB);
    }

    public List<MovieDB> getFavoritosMovieDBS(){
        return movieDBDao.getFavoritosMovieDBS();
    }

    public Boolean isFavorito(MovieDB movieDB) {
        return (getFavoritosMovieDBS().contains(movieDB)) ? true : false;
    }
}

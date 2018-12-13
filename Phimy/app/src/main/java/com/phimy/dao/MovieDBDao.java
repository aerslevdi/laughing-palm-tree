package com.phimy.dao;

import com.phimy.model.Cast;
import com.phimy.model.CastDBContainer;
import com.phimy.model.MovieDB;
import com.phimy.model.MovieDBContainer;
import com.phimy.model.VideoDB;
import com.phimy.model.VideoDBContainer;

import java.util.ArrayList;
import java.util.List;

import Utils.ResultListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDBDao extends DaoHelper {
    private ServiceMoviesDB serviceMovies;
    private String api_key = "5aa2e212bfa0373c59c3494bb068827f";
    Call<MovieDBContainer> call;
    List<MovieDB> favoritosMovieDBS = new ArrayList<MovieDB>();

    public MovieDBDao() {
        super("https://api.themoviedb.org/3/");
        serviceMovies = retrofit.create(ServiceMoviesDB.class);
    }

    public List<MovieDB> getFavoritosMovieDBS() {
        return favoritosMovieDBS;
    }

    public void getMovies(final ResultListener<List<MovieDB>> listenerDelController, Integer search) {

        switch (search) {
            case 0:
                this.call = serviceMovies.getPopularMovies(api_key);
                break; // optional
            case 1:
                this.call = serviceMovies.getPopularTv(api_key);
                break; // optional
            case 2:
                this.call = serviceMovies.getNowPlaying(api_key);
                break; // optional
            // You can have any number of case statements.
            default: // Optional
                this.call = serviceMovies.getPopularMovies(api_key);
                break;
        }
        this.call.enqueue(new Callback<MovieDBContainer>() {
            @Override
            public void onResponse(Call<MovieDBContainer> call, Response<MovieDBContainer> response) {
                MovieDBContainer movieContainer = response.body();
                List<MovieDB> movies = movieContainer.getMisMovies();
                listenerDelController.finish(movies);
            }

            @Override
            public void onFailure(Call<MovieDBContainer> call, Throwable t) {
                String i = "hola";
            }
        });
    }

    //TRAER CAST START
    public void getCast(final ResultListener<List<Cast>> castListener, Integer id) {
        retrofit2.Call<CastDBContainer> castDBContainerCall = serviceMovies.getCast(id, api_key);
        castDBContainerCall.enqueue(new Callback<CastDBContainer>() {
            @Override
            public void onResponse(retrofit2.Call<CastDBContainer> call, Response<CastDBContainer> response) {
                CastDBContainer castContainer = response.body();
                List<Cast> castList = castContainer.getFullCast();
                castListener.finish(castList);
            }

            @Override
            public void onFailure(retrofit2.Call<CastDBContainer> call, Throwable t) {

            }
        });
    }

    //TRAER CAST END

    //TRAER VIDEO START

    public void getVideo (final ResultListener<VideoDB> videoListener, Integer movie_id){
        Call<VideoDB> videoCall = serviceMovies.getVideo(movie_id, api_key);
        videoCall.enqueue(new Callback<VideoDB>() {
            @Override
            public void onResponse(Call<VideoDB> call, Response<VideoDB> response) {
                VideoDB videoContainer = response.body();
                videoListener.finish(videoContainer);
            }

            @Override
            public void onFailure(Call<VideoDB> call, Throwable t) {

            }
        });
    }

    public void getFavoritos(final ResultListener<List<MovieDB>> listenerDelController) {
        listenerDelController.finish(favoritosMovieDBS);
    }

    public void addFavoritos(MovieDB movieDB) {
        if (!favoritosMovieDBS.contains(movieDB)) {
            favoritosMovieDBS.add(movieDB);
        }
    }

    public void removeFavoritos(MovieDB movieDB) {
        int index = favoritosMovieDBS.indexOf(movieDB);
        if (index != -1) {
            // Remove the item and store it in a variable
            favoritosMovieDBS.remove(index);
        }
    }
}
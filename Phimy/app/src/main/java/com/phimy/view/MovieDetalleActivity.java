package com.phimy.view;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phimy.R;
import com.phimy.controller.ControllerMovieDB;
import com.phimy.model.Cast;
import com.phimy.model.MovieDB;
import com.phimy.view.adapter.MovieAdapter;
import com.phimy.view.adapter.SliderAdapter;
import com.phimy.view.fragment.DetalleFragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import Utils.ResultListener;

public class MovieDetalleActivity extends AppCompatActivity implements DetalleFragment.Listener {
    public static final String KEY_MOVIEDB = "movieDB";
    public static final String KEY_POS = "position";
    public static final String KEY_NAMEFRAG = "name";
    private DetalleFragment detalleFragment;
    private Toolbar toolbar;
    private List<MovieDB> movieData = new ArrayList<>();
    private ControllerMovieDB controllerMovieDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detalle);


        toolbar = findViewById(R.id.toolbarWid);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        Intent intent = getIntent();
        Bundle intentBundle = intent.getExtras();
        Object movieObj = intentBundle.getSerializable(KEY_MOVIEDB);
        String senderFragment = intentBundle.getString(KEY_NAMEFRAG);
        final Integer posicion = intentBundle.getInt(KEY_POS);
        final MovieDB movieDB = (MovieDB) movieObj;
        controllerMovieDB = new ControllerMovieDB();

        detalleFragment = new DetalleFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(detalleFragment.KEY_MOVIEDBFR, movieDB);
        detalleFragment.setArguments(bundle);


        final SliderAdapter adapter = new SliderAdapter(getSupportFragmentManager(), movieData);

        String holaaaaa = "TvFragment";
        final ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(posicion);

        if (senderFragment.equals(holaaaaa)) {
            controllerMovieDB.getTvMovies(new ResultListener<List<MovieDB>>() {
                @Override
                public void finish(List<MovieDB> result) {
                    adapter.setDatos(result);
                    viewPager.setCurrentItem(posicion);
                }
            }, this);

        } else if (senderFragment == "OnRatedFragment") {
            controllerMovieDB.getNowPlaying(new ResultListener<List<MovieDB>>() {
                @Override
                public void finish(List<MovieDB> result) {
                    adapter.setDatos(result);
                    viewPager.setCurrentItem(posicion);
                }
            }, this);
        } else if (senderFragment == "MovieFragment") {
            controllerMovieDB.getMovies(new ResultListener<List<MovieDB>>() {
                @Override
                public void finish(List<MovieDB> result) {
                    adapter.setDatos(result);
                    viewPager.setCurrentItem(posicion);
                }
            }, this);
        } else if (senderFragment == "FavoritoFragment") {
            controllerMovieDB.getFavoritos(new ResultListener<List<MovieDB>>() {
                @Override
                public void finish(List<MovieDB> result) {
                    adapter.setDatos(result);
                    viewPager.setCurrentItem(posicion);
                }
            }, this);
        } else {
            controllerMovieDB.getNowPlaying(new ResultListener<List<MovieDB>>() {
                @Override
                public void finish(List<MovieDB> result) {
                    adapter.setDatos(result);
                    viewPager.setCurrentItem(posicion);
                }
            }, this);
        }

    }

    @Override
    public void send(MovieDB movieDB) {
        Intent intent = new Intent(MovieDetalleActivity.this, MediaActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MediaActivity.KEY_MOVIEVID, movieDB);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

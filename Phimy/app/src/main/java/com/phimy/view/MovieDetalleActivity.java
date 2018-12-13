package com.phimy.view;

import android.content.Intent;
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
import com.phimy.model.Cast;
import com.phimy.model.MovieDB;
import com.phimy.view.adapter.SliderAdapter;
import com.phimy.view.fragment.DetalleFragment;

import java.util.List;

public class MovieDetalleActivity extends AppCompatActivity {
    public static final String KEY_MOVIEDB="movieDB";
    private DetalleFragment detalleFragment;
    private Toolbar toolbar;
    private List<MovieDB> movieData;

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
        Object movieObj = intent.getSerializableExtra(KEY_MOVIEDB);
        MovieDB movieDB = (MovieDB)movieObj;

        detalleFragment= new DetalleFragment();

        Bundle bundle = new Bundle();
        bundle.putSerializable(detalleFragment.KEY_MOVIEDBFR, movieDB);
        detalleFragment.setArguments(bundle);
        //TODO LISTA PELICULAS
        ViewPager viewPager = findViewById(R.id.viewPager);
        SliderAdapter adapter = new SliderAdapter(getSupportFragmentManager(), movieData);
        viewPager.setAdapter(adapter);

    }

}

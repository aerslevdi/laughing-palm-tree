package com.phimy.view;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.phimy.R;
import com.phimy.model.MovieDB;
import com.phimy.view.fragment.DetalleFragment;

public class MovieDetalleActivity extends AppCompatActivity {
    public static final String KEY_MOVIEDB="movieDB";
    private DetalleFragment detalleFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detalle);

        Intent intent = getIntent();
        Object movieObj = intent.getSerializableExtra(KEY_MOVIEDB);
        MovieDB movieDB = (MovieDB)movieObj;

        /*ImageView imageView= findViewById(R.id.detalleMovie);
        TextView textView=findViewById(R.id.title);

        String path = movieDB.getPoster_path();
        Glide.with(this).load("http://image.tmdb.org/t/p/w185/"+movieDB.getPoster_path()).into(imageView);
        textView.setText(movieDB.getTitle());*/

        detalleFragment= new DetalleFragment();
        FragmentManager fragmentManager= getSupportFragmentManager();

        Bundle bundle = new Bundle();
        bundle.putSerializable(detalleFragment.KEY_MOVIEDBFR, movieDB);
        detalleFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentoMovie, detalleFragment);
        fragmentTransaction.commit();

    }

}

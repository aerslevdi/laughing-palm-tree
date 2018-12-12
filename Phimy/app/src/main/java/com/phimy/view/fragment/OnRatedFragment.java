package com.phimy.view.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.phimy.R;
import com.phimy.controller.ControllerMovieDB;
import com.phimy.model.MovieDB;
import com.phimy.view.MovieDetalleActivity;
import com.phimy.view.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Utils.ResultListener;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnRatedFragment extends Fragment implements MovieAdapter.Receptor{
    private ControllerMovieDB controllerMovieDB;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_on_rated, container, false);

//-------------------------------------------------
        SharedPreferences sharedPrefs1;
        sharedPrefs1 = PreferenceManager.getDefaultSharedPreferences(getContext());
        String userSplashValue = sharedPrefs1.getString("settings_columns_values", "4");

        if (userSplashValue.equals ("1")) {
            // choice 1
        }
        else if (userSplashValue.equals("2")) {
            // choice 2
        }
        else if (userSplashValue.equals ("3")){
            // choice 3
        }

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        int pepe1= sharedPrefs.getInt("pref_pref4", 2);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.phimy_preferences", MODE_PRIVATE);

       //SharedPreferences.Editor editor = sharedPreferences.edit();
       int pepe;
       pepe = sharedPreferences.getInt("pref_pref4",2);

        // We retrieve foreground and background color value as a string
        String foregroundColor = sharedPreferences.getString("settings_columns", "2");
        String Color = sharedPreferences.getString("settings_columns_values", "2");
//------------------------------------------------

        this.controllerMovieDB=ControllerMovieDB.getInstance();
        loadRecyclerView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_status, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_status) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }

    private void loadRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.movieRecyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        Drawable imageFavorito= this.getResources().getDrawable(R.drawable.favoritered);
        Drawable imageNoFavorito= this.getResources().getDrawable(R.drawable.favoritegrey);
        MovieAdapter adapter = new MovieAdapter(this, new ArrayList<MovieDB>(), R.layout.movie_cardview,
                imageFavorito,imageNoFavorito);
        recyclerView.setAdapter(adapter);

        loadAdapterData(adapter, view);
    }

    private void loadAdapterData(final MovieAdapter adapter, View view) {
        controllerMovieDB.getNowPlaying(new ResultListener<List<MovieDB>>() {
            @Override
            public void finish(List<MovieDB> result) {
                adapter.setMovieList(result);
            }
        }, view.getContext());
    }

    @Override
    public void recibir(MovieDB movieDB) {
        Intent intent=new Intent(this.getActivity(), MovieDetalleActivity.class );
        Bundle bundle= new Bundle();
        bundle.putSerializable(MovieDetalleActivity.KEY_MOVIEDB, movieDB);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

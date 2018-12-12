package com.phimy.view.fragment;


import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
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
import com.phimy.view.InicioActivity;
import com.phimy.view.MovieDetalleActivity;
import com.phimy.view.adapter.MovieAdapter;

import java.util.ArrayList;
import java.util.List;

import Utils.ResultListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieFragment extends Fragment implements MovieAdapter.Receptor{
    private ControllerMovieDB controllerMovieDB;
    private MovieAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_movie, container, false);

        this.controllerMovieDB=ControllerMovieDB.getInstance();
        loadRecyclerView(view);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            Toast.makeText(getActivity(), "Clicked on " + item.getTitle(), Toast.LENGTH_SHORT)
                    .show();
        }
        return true;
    }

    private void loadRecyclerView(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.movieRecyclerView);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 3);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        Drawable imageFavorito= this.getResources().getDrawable(R.drawable.favoritered);
        Drawable imageNoFavorito= this.getResources().getDrawable(R.drawable.favoritegrey);
        adapter = new MovieAdapter(this, new ArrayList<MovieDB>(), R.layout.movie_cardview,
                imageFavorito,imageNoFavorito);
        recyclerView.setAdapter(adapter);
        loadAdapterData(adapter, view);
    }

    private void loadAdapterData(final MovieAdapter adapter, View view) {
        controllerMovieDB.getMovies(new ResultListener<List<MovieDB>>() {
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

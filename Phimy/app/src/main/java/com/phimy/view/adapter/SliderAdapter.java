package com.phimy.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.phimy.model.MovieDB;
import com.phimy.view.fragment.DetalleFragment;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    private List<MovieDB> datos;

    public SliderAdapter(FragmentManager fm, List<MovieDB> datos) {
        super(fm);
        this.datos = datos;
        for (MovieDB movieDB : datos){
            DetalleFragment detalleView = DetalleFragment.fabrica(movieDB);
            fragments.add(detalleView);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }
}

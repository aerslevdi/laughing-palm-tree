package com.phimy.view.fragment;


import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.phimy.R;
import com.phimy.controller.ControllerMovieDB;
import com.phimy.model.Cast;
import com.phimy.model.MovieDB;
import com.phimy.model.VideoDB;
import com.phimy.view.adapter.DetalleAdapter;

import java.util.ArrayList;
import java.util.List;

import Utils.ResultListener;


public class DetalleFragment extends Fragment implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener {

    public static final String KEY_MOVIEDBFR = "movieDB";
    private List<MovieDB> favoritos;
    String claveYoutube = "AIzaSyAWSqt1Xz3k4omNxLxIB8z7U56fEoszdSY";
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;
    private YouTubePlayerView youTubePlayerView;

    public DetalleFragment() {
        // Required empty public constructor
    }

    public static DetalleFragment fabrica(MovieDB movieDB) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetalleFragment.KEY_MOVIEDBFR, movieDB);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        Bundle bundle = getArguments();
        Object movieObj = bundle.getSerializable(KEY_MOVIEDBFR);
        final MovieDB movieDB = (MovieDB) movieObj;
        String path = movieDB.getPoster_path();
        ControllerMovieDB controller = new ControllerMovieDB();
        youTubePlayerView= view.findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(claveYoutube,this);

        //GET COMPONENTS

        TextView tituloView = view.findViewById(R.id.tituloPelicula);
        final DetalleAdapter actorAdapter = new DetalleAdapter(new ArrayList<Cast>());
        TextView fechaView = view.findViewById(R.id.fecha);
        TextView scoreView = view.findViewById(R.id.scoreNumero);
        TextView metaView = view.findViewById(R.id.scoreMeta);
        RecyclerView recyclerView = view.findViewById(R.id.actoresRecycler);
        ImageView trailerView = view.findViewById(R.id.imagenVideo);
        ImageView shareMovie = view.findViewById(R.id.share);
        TextView plot = view.findViewById(R.id.plot);
        FloatingActionButton fab = view.findViewById(R.id.fabButton);


        //SET DATA

        tituloView.setText(movieDB.getTitle());
        tituloView.setText(movieDB.getTitle());
        Glide.with(this).load("http://image.tmdb.org/t/p/w185/" + path).into(trailerView);
        fechaView.setText(movieDB.getRelease_date());
        scoreView.setText(movieDB.getPopularity().toString());
        metaView.setText(movieDB.getVote_count().toString());
        //TODO trailerView.setImageResource(movieDB.getTrailer());
        plot.setText(movieDB.getOverview());
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(actorAdapter);

        //SHARE

        shareMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.putExtra(Intent.EXTRA_SUBJECT, "Compartir en WhatsApp");
                share.putExtra(Intent.EXTRA_TEXT, "Te recomiendo" + movieDB.getTitle() + "/n Enviado desde PHIM");
                startActivity(Intent.createChooser(share, "Share link!"));
            }
        });

        //AGREGAR A FAVORITOS

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (favoritos == null) {
                    favoritos = new ArrayList<>();
                    favoritos.add(movieDB);
                } else {
                    favoritos.add(movieDB);
                }
                Snackbar.make(view, "La pelicula ha sido agregada a tu lista", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        favoritos.remove(movieDB);
                    }
                }).show();
            }
        });

        //TRAER CAST

        controller.getCast(view.getContext(), new ResultListener<List<Cast>>() {
            @Override
            public void finish(List<Cast> resultado) {
                actorAdapter.setCasts(resultado);

            }
        }, movieDB.getId());

        if (movieDB.getVideo() == true){
            controller.getVideoDB(view.getContext(), new ResultListener<VideoDB>() {
                @Override
                public void finish(VideoDB resultado) {

                }
            }, movieDB.getId());
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onPlaying() {

    }

    @Override
    public void onPaused() {

    }

    @Override
    public void onStopped() {

    }

    @Override
    public void onBuffering(boolean b) {

    }

    @Override
    public void onSeekTo(int i) {

    }
}

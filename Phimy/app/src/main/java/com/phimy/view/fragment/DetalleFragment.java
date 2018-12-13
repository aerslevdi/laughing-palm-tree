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
    private String videoKey;
    private TextView tituloView;
    private TextView fechaView;
    private TextView scoreView;
    private TextView metaView;
    private RecyclerView recyclerView;
    private ImageView trailerView;
    private ImageView shareMovie;
    private TextView plot;
    private FloatingActionButton fab;
    private String path;
    private MovieDB movieDB;

    public DetalleFragment() {
        // Required empty public constructor
    }

    public static DetalleFragment fabrica(MovieDB movie) {
        DetalleFragment fragment = new DetalleFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DetalleFragment.KEY_MOVIEDBFR, movie);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detalle, container, false);

        Bundle bundle = getArguments();
        Object movieObj = bundle.getSerializable(KEY_MOVIEDBFR);
        movieDB = (MovieDB) movieObj;
        path = movieDB.getPoster_path();
        ControllerMovieDB controller = new ControllerMovieDB();
        youTubePlayerView= view.findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(claveYoutube,this);

        //GET COMPONENTS

        tituloView = view.findViewById(R.id.tituloPelicula);
        final DetalleAdapter actorAdapter = new DetalleAdapter(new ArrayList<Cast>());
        fechaView = view.findViewById(R.id.fecha);
        scoreView = view.findViewById(R.id.scoreNumero);
        metaView = view.findViewById(R.id.scoreMeta);
        recyclerView = view.findViewById(R.id.actoresRecycler);
        trailerView = view.findViewById(R.id.imagenVideo);
        shareMovie = view.findViewById(R.id.share);
        plot = view.findViewById(R.id.plot);
        fab = view.findViewById(R.id.fabButton);


        //SET DATA

        tituloView.setText(movieDB.getTitle());
        tituloView.setText(movieDB.getTitle());
        Glide.with(this).load("http://image.tmdb.org/t/p/w185/" + path).into(trailerView);
        fechaView.setText(movieDB.getRelease_date());
        scoreView.setText(movieDB.getPopularity().toString());
        metaView.setText(movieDB.getVote_count().toString());
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

        //TRAER VIDEO
        controller.getVideoDB(view.getContext(), new ResultListener<VideoDB>() {
            @Override
            public void finish(VideoDB resultado) {
                videoKey = resultado.getKey();

            }
        }, movieDB.getId());

       // if (movieDB.getVideo() == true && controller.getVideoDB(); = null){}




        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b){
            youTubePlayer.cueVideo(videoKey);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Glide.with(this).load("http://image.tmdb.org/t/p/w185/" + path).into(trailerView);
    }
    protected YouTubePlayer.Provider getYouTubePlayerProvider(){
        return  youTubePlayerView;

    }
    protected void OnactivityResult(int requestCode, int resultcode, Intent data){
        if (resultcode==1){
            getYouTubePlayerProvider().initialize(claveYoutube,this);
        }
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

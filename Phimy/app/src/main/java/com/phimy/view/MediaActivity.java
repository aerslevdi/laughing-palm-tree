package com.phimy.view;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.phimy.R;

import java.io.IOException;

import Utils.ThemeUtils;

public class MediaActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.PlaybackEventListener{
    private SurfaceView surfaceView;
    private MediaPlayer mediaPlayer;
    private SurfaceHolder surfaceHolder;

    YouTubePlayerView youTubePlayerView;
    String claveYoutube = "AIzaSyAWSqt1Xz3k4omNxLxIB8z7U56fEoszdSY";
    //private static final String VIDEO_PATH="http://mic.duytan.edu.vn:83/FINAL.mp4";
    private static final String VIDEO_PATH="https://www.youtube.com/watch?v=zSWdZVtXT7E";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ThemeUtils.setcTheme(ThemeUtils.APP_ALTERNATIVA3);
        ThemeUtils.onActivityCreateSetTheme(this);
        setContentView(R.layout.activity_media);

        youTubePlayerView= (YouTubePlayerView)findViewById(R.id.youtube_view);
        youTubePlayerView.initialize(claveYoutube,this);

    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean fueRestaurado) {
            if (!fueRestaurado){
            youTubePlayer.cueVideo("zSWdZVtXT7E");
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if (youTubeInitializationResult.isUserRecoverableError()) {
            youTubeInitializationResult.getErrorDialog(this, 1).show();
        } else {
            String error = "Error al iniciar youtube" + youTubeInitializationResult.toString();
            Toast.makeText(getApplication(), error, Toast.LENGTH_SHORT).show();
        }
    }

    protected void OnactivityResult(int requestCode, int resultcode, Intent data){
        if (resultcode==1){
           getYouTubePlayerProvider().initialize(claveYoutube,this);
        }
    }

    protected YouTubePlayer.Provider getYouTubePlayerProvider(){
        return  youTubePlayerView;

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

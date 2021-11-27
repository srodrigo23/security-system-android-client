package com.example.socket;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * https://stackoverflow.com/questions/48518972/exoplayer-hlsmediasource-deprecated
 * https://stackoverflow.com/questions/42228653/exoplayer-adaptive-hls-streaming
 */

public class MainActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;

    String videoURL = "http://192.168.100.8:5000/video/hls_out.m3u8";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = findViewById(R.id.exoplayerview);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener( task -> {
            if (!task.isSuccessful()) {
                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                return;
            }
            // Get new FCM registration token
            String token = task.getResult();
            Log.d("Token", token);
        });

        try {
            // track selector is used to navigate between video using a default seekbar.
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory());
            // we are adding our track selector to exoplayer.
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                    Util.getUserAgent(this, "ExoPlayer"));
            Uri videouri = Uri.parse(videoURL);
            // we are creating a variable for extractor factory and setting it to default extractor factory.
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            // This is the MediaSource representing the media to be played.
            HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(videouri);
            // inside our exoplayer view we are setting our player
            playerView.setPlayer(simpleExoPlayer);
            // we are preparing our exoplayer with media source.
            simpleExoPlayer.prepare(hlsMediaSource);
            // we are setting our exoplayer when it is ready.
            simpleExoPlayer.setPlayWhenReady(true);
        } catch (Exception e) {
            Log.e("TAG", "Error : " + e.toString());
        }
    }
}
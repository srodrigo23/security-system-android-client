package com.example.socket;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Backend app2.py flask??
 * https://www.geeksforgeeks.org/how-to-build-a-simple-android-app-with-flask-backend/
 * https://medium.com/analytics-vidhya/how-to-make-client-android-application-with-flask-for-server-side-8b1d5c55446e
 *
 * https://stackoverflow.com/questions/28386567/exo-player-dash-streaming-example
 * https://stackoverflow.com/questions/55294520/how-to-initialise-exoplayer-to-play-dash-mpd-videos
 *
 *
 */

public class MainActivity extends AppCompatActivity {

    private TextView pagenameTextView;

    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;

//    String videoURL = "https://media.geeksforgeeks.org/wp-content/uploads/20201217163353/Screenrecorder-2020-12-17-16-32-03-350.mp4";
    String videoURL = "http://192.168.100.8:5000/video/dash.mpd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playerView = findViewById(R.id.exoplayerview);

        try {

            // bandwisthmeter is used for getting default bandwidth
//            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            // track selector is used to navigate between video using a default seekbar.
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory());

            // we are adding our track selector to exoplayer.
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            // we are parsing a video url
            // and parsing its video uri.
            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "ExoPlayer"));
            Uri videouri = Uri.parse(videoURL);
            DashMediaSource dashMediaSource = new DashMediaSource(videouri, dataSourceFactory,
                    new DefaultDashChunkSource.Factory(dataSourceFactory), null, null);
            // we are creating a variable for datasource factory
            // and setting its user agent as 'exoplayer_view'
//            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");

            // we are creating a variable for extractor factory
            // and setting it to default extractor factory.
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            // we are creating a media source with above variables
            // and passing our event handler as null,
//            MediaSource mediaSource = new ExtractorMediaSource(videouri, dataSourceFactory, extractorsFactory, null, null);

            // inside our exoplayer view
            // we are setting our player
            playerView.setPlayer(simpleExoPlayer);

            // we are preparing our exoplayer
            // with media source.
            simpleExoPlayer.prepare(dashMediaSource);

            // we are setting our exoplayer
            // when it is ready.
            simpleExoPlayer.setPlayWhenReady(true);

        } catch (Exception e) {
            // below line is used for
            // handling our errors.
            Log.e("TAG", "Error : " + e.toString());
        }



//        pagenameTextView = findViewById(R.id.pagename);
//
//        OkHttpClient okHttpClient = new OkHttpClient(); // creating a client
//        Request request = new Request.Builder().url("http://192.168.100.8:5000/").build();// building a request


//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            // called if we get a
//            // response from the server
//            public void onResponse(@NotNull Call call, @NotNull Response response){
//                runOnUiThread(() -> {
//                    try {
//                        pagenameTextView.setText(response.body().string());
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                });
//
//            }
//
//            @Override
//            // called if server is unreachable
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                runOnUiThread(() -> {
//                    Toast.makeText(MainActivity.this, "server down", Toast.LENGTH_SHORT).show();
//                    pagenameTextView.setText("error connecting to the server");
//                });
//            }
//        });
    }

}
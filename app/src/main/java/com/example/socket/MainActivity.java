package com.example.socket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

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
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

/**
 * https://stackoverflow.com/questions/48518972/exoplayer-hlsmediasource-deprecated
 * https://stackoverflow.com/questions/42228653/exoplayer-adaptive-hls-streaming
 * https://danielme.com/2018/12/19/diseno-android-menu-lateral-con-navigation-drawer/
 */

public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener,
        DrawerLayout.DrawerListener
{

    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    String videoURL = "http://192.168.100.8:5000/video/hls_out.m3u8";

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(findViewById(R.id.toolbar));

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        header.findViewById(R.id.header_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, getString(R.string.app_name),
                        Toast.LENGTH_SHORT).show();
            }
        });

        MenuItem menuItem = navigationView.getMenu().getItem(0);
        onNavigationItemSelected(menuItem);
        menuItem.setChecked(true);

//        playerView = findViewById(R.id.exoplayerview);

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener( task -> {
            if (!task.isSuccessful()) {
                Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                return;
            }
            // Get new FCM registration token
            String token = task.getResult();
            Log.d("Token", token);
        });

//        try {
//            // track selector is used to navigate between video using a default seekbar.
//            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory());
//            // we are adding our track selector to exoplayer.
//            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
//            DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
//                    Util.getUserAgent(this, "ExoPlayer"));
//            Uri videouri = Uri.parse(videoURL);
//            // we are creating a variable for extractor factory and setting it to default extractor factory.
//            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
//            // This is the MediaSource representing the media to be played.
//            HlsMediaSource hlsMediaSource = new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(videouri);
//            // inside our exoplayer view we are setting our player
//            playerView.setPlayer(simpleExoPlayer);
//            // we are preparing our exoplayer with media source.
//            simpleExoPlayer.prepare(hlsMediaSource);
//            // we are setting our exoplayer when it is ready.
//            simpleExoPlayer.setPlayWhenReady(true);
//        } catch (Exception e) {
//            Log.e("TAG", "Error : " + e.toString());
//        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerClosed(@NonNull View drawerView) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int title;
        switch (item.getItemId()) {
            case R.id.nav_camera:
                title = R.string.menu_camera;
                break;
            case R.id.nav_gallery:
                title = R.string.menu_gallery;
                break;
            case R.id.nav_manage:
                title = R.string.menu_tools;
                break;
            case R.id.nav_share:
                title = R.string.menu_share;
                break;
            case R.id.nav_send:
                title = R.string.menu_send;
                break;
            default:
                throw new IllegalArgumentException("menu option not implemented!!");
        }
//        Fragment fragment = HomeContentFragment.newInstance(getString(title));
//        getSupportFragmentManager()
//                .beginTransaction()
//                .setCustomAnimations(R.anim.nav_enter, R.anim.nav_exit)
//                .replace(R.id.home_content, fragment)
//                .commit();
//        setTitle(getString(title));
//        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
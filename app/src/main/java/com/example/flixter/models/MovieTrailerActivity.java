package com.example.flixter.models;

import android.os.Bundle;
import android.util.Log;

import com.example.flixter.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    int id;
    Movie movie;
    public static final String TRAILER_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=28460c40a4afd80b08ad39ad987de33a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

//        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));



        final String videoID = (String) getIntent().getStringExtra("videoID");



//        videoID = "tKodtNFpzBA";
//        final String videoID = getIntent().;

        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        playerView.initialize("AIzaSyB-CMakXTdlPpMG26qWSXdEJjgz-JPNN9A", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(videoID);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.e("MovieTrailerActivity","Error initializing YouTube player");
            }
        });
    }
}
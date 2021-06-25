package com.example.flixter.models;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.flixter.R;
import com.example.flixter.databinding.ActivityMovieDetailsBinding;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import okhttp3.Headers;


public class MovieDetailsActivity extends YouTubeBaseActivity {

    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;
    Context context;
    long id;
    String videoID;
    public static final String TRAILER_URL = "https://api.themoviedb.org/3/movie/%d/videos?api_key=28460c40a4afd80b08ad39ad987de33a";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_movie_details);
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for movie: %s", movie.getTitle()));
        tvTitle = (TextView) binding.tvTitle;
//                (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) binding.tvOverview2;
//                (TextView) findViewById(R.id.tvOverview2);
        rbVoteAverage = (RatingBar) binding.rbVoteAverage;
//                (RatingBar) findViewById(R.id.rbVoteAverage);
        ivPoster = (ImageView) binding.ivPoster2;
//                (ImageView) findViewById(R.id.ivPoster2);
        id = movie.getID();
        context = (Context) this;





        ivPoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(context, MovieTrailerActivity.class);
                if (videoID != null) {
//                    intent.putExtra("videoID", videoID);
//                    context.startActivity(intent);

                    YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);
                    ivPoster.setVisibility(View.GONE);
                    playerView.setVisibility(View.VISIBLE);

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
        });


        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Movie: %s", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        String imageURL;
        int ph;
        // if phone landscape, then set imageurl = backdrop
        // else set imageurl = poster

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            imageURL = movie.getBackdropPath();
            ph = R.drawable.flicks_backdrop_placeholder;
        } else {
            imageURL = movie.getPosterPath();
            ph = R.drawable.flicks_movie_placeholder;
        }

        int radius = 30;
        Glide.with(context).load(imageURL).transform(new CenterInside(), new RoundedCorners(radius)).placeholder(ph).into(ivPoster);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(String.format(TRAILER_URL,id), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d("MovieTrailerActivity","onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i("MovieTrailerActivity", "Results: " + results.toString());
                    if (results.length() >= 1){
                        JSONObject firstElem = results.getJSONObject(0);
                        videoID = firstElem.getString("key");
                        Log.i("MovieTrailerActivity","videoID: " + videoID);
                    } else {
                        Log.i("MovieTrailerActivity","No trailer available");
                        return;
                    }

                } catch (JSONException e) {
                    Log.e("MovieTrailerActivity","Hit JSON exception", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d("MovieTrailerActivity","onFailure");
            }
        });

    }
}
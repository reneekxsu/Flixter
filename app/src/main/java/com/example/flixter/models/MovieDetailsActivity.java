package com.example.flixter.models;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.flixter.R;

import org.parceler.Parcels;



public class MovieDetailsActivity extends AppCompatActivity {

    Movie movie;
    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for movie: %s", movie.getTitle()));
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvOverview = (TextView) findViewById(R.id.tvOverview2);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivPoster = (ImageView) findViewById(R.id.ivPoster2);
        context = (Context) this;

        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Movie: %s", movie.getTitle()));

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage / 2.0f);

        String imageURL;
        imageURL = movie.getPosterPath();
        Glide.with(context).load(imageURL).placeholder(R.drawable.flicks_movie_placeholder).into(ivPoster);
    }
}
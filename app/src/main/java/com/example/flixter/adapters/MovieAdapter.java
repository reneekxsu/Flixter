package com.example.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.flixter.R;
import com.example.flixter.databinding.ItemsMovieBinding;
import com.example.flixter.models.Movie;
import com.example.flixter.models.MovieDetailsActivity;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;
    ItemsMovieBinding binding;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @NotNull
    @Override
    // involves inflating a layout from XML and returning the holder
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter","onCreateViewHolder");
        binding = ItemsMovieBinding.inflate(LayoutInflater.from(context), parent, false);
        //View movieView = LayoutInflater.from(context).inflate(R.layout.items_movie,parent,false);
        View view = binding.getRoot();
        return new ViewHolder(view);
    }

    @Override
    // involves populating data into the item through holder
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder" + position);
        // get movie at passed in position
        Movie movie = movies.get(position);
        // bind movie data into VH
        holder.bind(movie);
    }

    @Override
    // returns total count of items in list
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
                tvTitle = binding.tvTitle;
                tvOverview = binding.tvOverview;
                ivPoster = binding.ivPoster;

                binding.getRoot().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            Movie movie = movies.get(position);
                            Intent intent = new Intent(context, MovieDetailsActivity.class);
                            intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                            context.startActivity(intent);
                        }
                    }
                });
        }


        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
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
            int margin = 0;
//            Glide.with(context).load(imageURL).placeholder(ph).into(ivPoster);
            Glide.with(context).load(imageURL).transform(new CenterInside(), new RoundedCorners(radius)).placeholder(ph).into(ivPoster);
        }
    }
}

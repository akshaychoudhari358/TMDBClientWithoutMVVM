package com.akshay.tmdbclient.view;

import android.content.Intent;
import android.os.Bundle;

import com.akshay.tmdbclient.model.Movie;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.akshay.tmdbclient.R;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;
    private ImageView movieImage;
    private String image;
    private TextView movieTitle, movieSynopsis, movieRating, movieReleaseDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        movieImage = (ImageView) findViewById(R.id.ivMovieLarge);
        movieTitle = (TextView) findViewById(R.id.tvMovieTitle);
        movieSynopsis = (TextView) findViewById(R.id.tvPlotsynopsis);
        movieRating = (TextView) findViewById(R.id.tvMovieRating);
        movieReleaseDate = (TextView) findViewById(R.id.tvReleaseDate);

        movieImage = (ImageView) findViewById(R.id.ivMovieLarge);

        Intent intent = getIntent();

        if (intent.hasExtra("movie")) {
            movie = getIntent().getParcelableExtra("movie");

            image = movie.getPosterPath();
            String path = "https://image.tmdb.org/t/p/w500" + image;
            Glide.with(this).load(path).placeholder(R.drawable.ic_launcher_background).into(movieImage);

            getSupportActionBar().setTitle(movie.getTitle());

            movieTitle.setText(movie.getTitle());
            movieSynopsis.setText(movie.getOverview());
            movieRating.setText(Double.toString(movie.getVoteAverage()));
            movieReleaseDate.setText(movie.getReleaseDate());
        }


    }
}
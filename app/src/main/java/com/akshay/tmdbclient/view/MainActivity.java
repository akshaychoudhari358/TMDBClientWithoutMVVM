package com.akshay.tmdbclient.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Toast;

import com.akshay.tmdbclient.R;
import com.akshay.tmdbclient.adapter.MovieAdapter;
import com.akshay.tmdbclient.model.Movie;
import com.akshay.tmdbclient.model.MovieDBResponse;
import com.akshay.tmdbclient.service.MovieDataService;
import com.akshay.tmdbclient.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Movie> movies;
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("TMDB Popular Movies Today");
        getPopularMovie();

        swipeRefreshLayout = findViewById(R.id.swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPopularMovie();
            }
        });
    }

    public void getPopularMovie(){

        final MovieDataService movieDataService = RetrofitInstance.getService();

       Call<MovieDBResponse> call = movieDataService.getPopularMovies(this.getString(R.string.api_key));

       call.enqueue(new Callback<MovieDBResponse>() {
           @Override
           public void onResponse(Call<MovieDBResponse> call, Response<MovieDBResponse> response) {

               MovieDBResponse movieDBResponse = response.body();
             //  Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_LONG).show();

               if(movieDBResponse != null && movieDBResponse.getResults() != null){

                   movies = (ArrayList<Movie>) movieDBResponse.getResults();
                   showOnRecyclerView();
               }
           }

           @Override
           public void onFailure(Call<MovieDBResponse> call, Throwable t) {
               Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
           }
       });

    }

    private void showOnRecyclerView() {

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        movieAdapter = new MovieAdapter(this, movies);

        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

    }
}
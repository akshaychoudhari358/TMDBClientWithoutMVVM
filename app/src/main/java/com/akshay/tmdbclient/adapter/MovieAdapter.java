package com.akshay.tmdbclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akshay.tmdbclient.R;
import com.akshay.tmdbclient.model.Movie;
import com.akshay.tmdbclient.view.MovieActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> moviesArrayList;

    public MovieAdapter(Context context, ArrayList<Movie> moviesArrayList) {
        this.context = context;
        this.moviesArrayList = moviesArrayList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_list_item, parent,false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.movieTitle.setText(moviesArrayList.get(position).getOriginalTitle());
        holder.rate.setText(Double.toString(moviesArrayList.get(position).getVoteAverage()));

        String imagePath = "https://image.tmdb.org/t/p/w500" + moviesArrayList.get(position).getPosterPath();
        Glide.with(context).load(imagePath).placeholder(R.drawable.ic_launcher_background).into(holder.movieImage);

    }

    @Override
    public int getItemCount() {
        return moviesArrayList.size();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        public TextView movieTitle, rate;
        public ImageView movieImage;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = (ImageView) itemView.findViewById(R.id.ivMovie);
            rate=(TextView) itemView.findViewById(R.id.tvRating);
            movieTitle=(TextView) itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();

                    if(position!= RecyclerView.NO_POSITION){
                        Movie selectMovie = moviesArrayList.get(position);

                        Intent intent = new Intent(context, MovieActivity.class);
                        intent.putExtra("movie", selectMovie);
                        context.startActivity(intent);



                    }
                }
            });

        }

    }
}

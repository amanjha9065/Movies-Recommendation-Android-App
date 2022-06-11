package com.amanjha.recommendationmovies;


import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;


public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.myViewHolder> {
    private ArrayList<Movie_detail> movieList;
    Context context;


    public recyclerAdapter(ArrayList<Movie_detail> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }



    public class myViewHolder extends RecyclerView.ViewHolder {
        private TextView movieName;
        private TextView movieCategory;
        private ImageView imageView;
        private Button btnLike;

        public myViewHolder(View view) {
            super(view);
            movieName = view.findViewById(R.id.moviename);
            movieCategory = view.findViewById(R.id.moviecategory);
            imageView = view.findViewById(R.id.thumbnail);
            btnLike = view.findViewById(R.id.likebtn);

        }




    }

    @NonNull
    @Override
    public recyclerAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent, false);
        return new myViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull recyclerAdapter.myViewHolder holder, int position) {
        String name = movieList.get(position).getName();
        String category = movieList.get(position).getCategory();
        holder.movieName.setText(name);
        holder.movieCategory.setText(category);

        Glide.with(context).load(movieList.get(position).getImage()).into(holder.imageView);

        SharedPreferences sh = context.getSharedPreferences("LikesData", MODE_PRIVATE);
        SharedPreferences.Editor myEdit = sh.edit();

        holder.btnLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int likeCount = sh.getInt(movieList.get(position).getCategory(), 0);
                if(holder.btnLike.getText().equals("LIKED")) {
                    holder.btnLike.setText("LIKE");
                    myEdit.putInt(movieList.get(position).getCategory(),likeCount-1).apply();
                }else {
                    holder.btnLike.setText("LIKED");
                    myEdit.putInt(movieList.get(position).getCategory(),likeCount+1).apply();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

}

package com.amanjha.recommendationmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ArrayList<Movie_detail> moviesList;
    private RecyclerView recyclerView;
    SharedPreferences sh, shHome;
    SharedPreferences.Editor myEdit, myEditHome;
    private int documentary = 0, hollywood = 0, animation = 0;
    Button btnToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recyclerView = findViewById(R.id.recycler_view_movie);
        sh = getSharedPreferences("LikesData", MODE_PRIVATE);
        myEdit = sh.edit();

        documentary = sh.getInt("Documentary", 0);
        animation = sh.getInt("Animation", 0);
        hollywood = sh.getInt("Hollywood", 0);
        btnToggle = findViewById(R.id.changetype);

        shHome = getSharedPreferences("currentUser", MODE_PRIVATE);
        myEditHome = shHome.edit();


        btnToggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean type = sh.getBoolean("TYPE", false);
                if (type == false) {
                    myEdit.putBoolean("TYPE", true).apply();
                    btnToggle.setText("Recommended");
                    new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Ftaxonomy%2Fterm%2F590757%2F0%2Ffeed");
                    new JSONTask().execute(" https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fhollywood%2Ffeed");
                    new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fanimation%2Ffeed");

                } else {
                    myEdit.putBoolean("TYPE", false).apply();
                    btnToggle.setText("All");
                    if (documentary == 0 && animation == 0 && hollywood == 0) {
                        new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Ftaxonomy%2Fterm%2F590757%2F0%2Ffeed");
                        new JSONTask().execute(" https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fhollywood%2Ffeed");
                        new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fanimation%2Ffeed");

                    } else if (documentary >= animation && documentary >= hollywood) {
                        new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Ftaxonomy%2Fterm%2F590757%2F0%2Ffeed");
                    } else if (animation > hollywood) {
                        new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fanimation%2Ffeed");

                    } else {
                        new JSONTask().execute(" https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fhollywood%2Ffeed");
                    }
                }
            }
        });

        if (documentary == 0 && animation == 0 && hollywood == 0) {
            new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Ftaxonomy%2Fterm%2F590757%2F0%2Ffeed");
            new JSONTask().execute(" https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fhollywood%2Ffeed");
            new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fanimation%2Ffeed");

        } else if (documentary >= animation && documentary >= hollywood) {
            new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Ftaxonomy%2Fterm%2F590757%2F0%2Ffeed");
        } else if (animation > hollywood) {
            new JSONTask().execute("https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fanimation%2Ffeed");

        } else {
            new JSONTask().execute(" https://api.rss2json.com/v1/api.json?rss_url=https%3A%2F%2Fwww.filmfestivals.com%2Fchannel%2Ffilm%2Fhollywood%2Ffeed");
        }

        moviesList = new ArrayList<>();
    }

    public void logout(View view) {
        Boolean isLoggedIn = shHome.getBoolean("isLoggedIn", false);
        if (isLoggedIn == true) {
                myEditHome.putBoolean("isLoggedIn", false);
                Intent intent = new Intent(HomeActivity.this, LoginnActivity.class);
                startActivity(intent);
                finish();
        }
    }

    public class JSONTask extends AsyncTask<String, String, ArrayList<Movie_detail>> {

        @Override
        protected ArrayList<Movie_detail> doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();

                JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("items");


                for (int i = 0; i < parentArray.length(); i++) {
                    Movie_detail movie_detail = new Movie_detail();
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    movie_detail.setName(finalObject.getString("title"));
                    movie_detail.setImage(finalObject.getString("thumbnail"));

                    JSONArray categories = finalObject.getJSONArray("categories");


                    for (int j = 0; j < categories.length(); j++) {
                        if (categories.get(j).toString().equals("Documentary") || categories.get(j).toString().equals("Animation") || categories.get(j).toString().equals("Hollywood")) {
                            movie_detail.setCategory(categories.get(j).toString());
                            break;
                        }
                    }
                    moviesList.add(movie_detail);

                }

                return moviesList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Movie_detail> result) {
            setAdapter();
            super.onPostExecute(result);
        }
    }


    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(moviesList, HomeActivity.this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}
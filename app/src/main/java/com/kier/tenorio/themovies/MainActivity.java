package com.kier.tenorio.themovies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ListAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CustomMovieAdapter myAdapter;
    private Movie[] moviesArray;


    @Override
    public void onStart()
    {
        super.onStart();
        new FetchMovieTask().execute();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView movieListView= (ListView) findViewById(R.id.listView_movies);

        String [] data ={"SpiderMan1","SpiderMan2","SpiderMan3"};
        //List<String> movieList=new ArrayList<String>(Arrays.asList(data));

       // myAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,movieList);
        List <Movie> movieList=new ArrayList<Movie>();




        myAdapter=new CustomMovieAdapter(this,R.layout.list_item_movies,movieList);
        movieListView.setAdapter(myAdapter);

        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
          @Override
            public void onItemClick(AdapterView<?>parent, View view, int position,long id)
          {
              Movie movieToDisplay=moviesArray[position];
              Intent intent=new Intent(MainActivity.this,MovieDetails.class).putExtra("currentMovie",movieToDisplay);
              startActivity(intent);
          }
        }
        );

    }


    public class FetchMovieTask extends AsyncTask<Void,Void,Movie[]>
    {
        private final String LOG_TAG=FetchMovieTask.class.getSimpleName();


        private Movie[] populateMoviesFromJSON(String jsonString) throws JSONException
        {
            JSONObject moviesJson = new JSONObject(jsonString);
            JSONArray jsonMoviesArray=moviesJson.getJSONArray("results");

            moviesArray=new Movie[jsonMoviesArray.length()];


            for(int i=0;i<jsonMoviesArray.length();i++)
            {
                String imgPath="";
                String title="";
                String plot="";
                double userRating=0.0;
                String date="";

                JSONObject currentMovie=jsonMoviesArray.getJSONObject(i);

                imgPath=currentMovie.getString("poster_path");
                title=currentMovie.getString("original_title");
                plot=currentMovie.getString("overview");
                userRating=currentMovie.getDouble("vote_average");
                date=currentMovie.getString("release_date");

                moviesArray[i]=new Movie(imgPath,title,plot,userRating,date);

            }

            return moviesArray;
        }

        @Override
        protected Movie[] doInBackground(Void...params)
        {
            HttpURLConnection urlConnection=null;
            BufferedReader reader=null;
            String moviesJsonString=null;

            try
            {
                final String baseURL="https://api.themoviedb.org/3/movie/popular?api_key="+BuildConfig.THEMOVIEDB_API_KEY;

                URL url = new URL(baseURL);
                Log.v(LOG_TAG,"Built Uri "+baseURL);


                urlConnection=(HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream =urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream==null)
                    return null;

                reader=new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while((line=reader.readLine())!=null){

                    buffer.append(line+"\n");
                }

                if(buffer.length()==0)
                    return null;

                moviesJsonString=buffer.toString();
                Log.v(LOG_TAG,"JSON String "+moviesJsonString);
            }
            catch(IOException e)
            {
                Log.e(LOG_TAG,"Error",e);
                return null;
            }
            finally {
                if(urlConnection!=null)
                    urlConnection.disconnect();
                if(reader!=null)
                    try{
                        reader.close();
                    }
                    catch (final IOException e)
                    {
                        Log.e(LOG_TAG,"Error closing stream",e);
                    }

            }

            try
            {
                return populateMoviesFromJSON(moviesJsonString);

            }
            catch (JSONException e)
            {
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

         return null;
        }

        @Override
        protected void onPostExecute(Movie[] movie)
        {
            if(movie==null)
            {
                return;
            }
            else {
                myAdapter.clear();
                for (Movie currentMovie : movie) {

                    myAdapter.add(currentMovie);
                }
            }
        }

    }
}

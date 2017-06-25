package com.kier.tenorio.themovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by Kier on 11/02/2017.
 */
public class MovieDetails extends AppCompatActivity{

    private final static String LOG_TAG=MovieDetails.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle data= getIntent().getExtras();
        Movie currentMovie=data.getParcelable("currentMovie");

        Log.v(LOG_TAG,"User Ratings for this Movie: "+currentMovie.getUserRating());
        setDataToViews(currentMovie);

    }


    private void setDataToViews(Movie movie)
    {
        TextView txtTitle=(TextView) findViewById(R.id.txtMovieTitle);
        ImageView imageView=(ImageView) findViewById(R.id.imageView);
        RatingBar userRating=(RatingBar) findViewById(R.id.userRating);
        TextView txtPlot =(TextView)findViewById(R.id.txtPlot);

        final String BASE_URL="http://image.tmdb.org/t/p/w185";
        float ratings=(float)movie.getUserRating();

        Picasso.with(this).load(BASE_URL+movie.getImageUrl()).into(imageView);


        txtTitle.setText(movie.getOriginalTitle());
        userRating.setRating(2.f);
        txtPlot.setText(movie.getPlot());
    }
}

package com.kier.tenorio.themovies;

import android.content.Context;
import android.text.NoCopySpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Kier on 07/02/2017.
 */
public class CustomMovieAdapter extends ArrayAdapter<Movie> {


    public CustomMovieAdapter(Context context, int resource, List<Movie> movies)
    {
        super(context,resource,movies);
    }


    @Override
    public View getView(int position,View convertView,ViewGroup parent)
    {
        View view=convertView;
         final String baseURL="http://image.tmdb.org/t/p/w185";
        if(view==null)
        {
            LayoutInflater inflater=LayoutInflater.from(getContext());
            view=inflater.inflate(R.layout.list_item_movies,null);
        }

        Movie currentMovie=getItem(position);

        if(currentMovie!=null)
        {
            ImageView imageView=(ImageView)view.findViewById(R.id.imgMovie);
            TextView txtTitle=(TextView)view.findViewById(R.id.txtTitle);

            Picasso.with(getContext()).load(baseURL+currentMovie.getImageUrl()).into(imageView);
            txtTitle.setText(currentMovie.getOriginalTitle());
        }


        return view;
    }
}

package com.example.lu_xi.hw9_xinghe_lu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;


public class MovieFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //HashMap<String,?> movie;
    private String movieID;
    //MovieData movieData = null;
    ImageView movieImageIV;
    TextView movieNameTV,movieYearIV,movieDescriptionIV,movieStarsIV,movieDirectorIV,movieLengthIV,
            movieRatingIV;
    RatingBar movieRatingBar;
    private int position = 0;
    ShareActionProvider mShareActionProvider;
    String movieDescrip;
    final Firebase ref = new Firebase("https://crackling-fire-8001.firebaseio.com/moviedata");
    HashMap<String, String> movie;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public MovieFragment() {
        // Required empty public constructor
    }

    public void setPage(View view){//used to set movie page

        //movieData = new MovieData();
        /*int movieId = 0;
        HashMap movie = movieData.getItem(movieId);
        while(!(movie.get("name")).equals(movieTitle)){
            movieId++;
            movie = movieData.getItem(movieId);
        }*/

        //int imageId = (Integer)movie.get("image");
        String movieName = (String)movie.get("name");
        String movieStars = (String)movie.get("stars");
        String movieYear = (String)movie.get("year");
        String movieDescription = (String)movie.get("description");
        movieDescrip = movieDescription;
        String movieDirector = (String)movie.get("director");
        String movieLength = (String)movie.get("length");
        double movieRating = Double.parseDouble(movie.get("rating"));


        movieNameTV = (TextView)view.findViewById(R.id.movieName);
        movieImageIV = (ImageView)view.findViewById(R.id.movieImage);
        movieStarsIV = (TextView)view.findViewById(R.id.stars);
        movieYearIV = (TextView)view.findViewById(R.id.year);
        movieDescriptionIV = (TextView)view.findViewById((R.id.description));
        movieDirectorIV = (TextView)view.findViewById(R.id.director);
        movieLengthIV = (TextView)view.findViewById(R.id.length);
        movieRatingIV = (TextView)view.findViewById(R.id.rating);
        movieRatingBar = (RatingBar)view.findViewById(R.id.ratingBar);

        movieNameTV.setText(movieName);
        //movieImageIV.setImageResource(imageId);
        Picasso.with(getContext()).load(movie.get("url")).into(movieImageIV);
        movieImageIV.setTransitionName(movieName);
        movieStarsIV.setText(movieStars);
        movieDescriptionIV.setText(movieDescription);
        movieYearIV.setText("("+movieYear+")");
        movieDirectorIV.setText(movieDirector);
        movieLengthIV.setText(movieLength);
        movieRatingIV.setText(String.valueOf(movieRating)+"/10");
        movieRatingBar.setRating((float)movieRating/2);
    }

    public static MovieFragment newInstance(int position, String movieId) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        args.putInt("position", position);

        args.putString("movieID", movieId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.layout_movie_fragment, container, false);
        position=getArguments().getInt("position");
        movieID = getArguments().getString("movieID");
        Log.d("Movie ID: ", movieID);
        ref.child(movieID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("OnDataChange : ", dataSnapshot.toString());
                HashMap<String, String> movie = (HashMap<String, String>) dataSnapshot.getValue();
                Log.d("Movie description: ", movie.get("description"));
                setMovie(movie);
                setPage(v);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        return v;
    }

    public void setMovie(HashMap<String, String> movie){
        this.movie = movie;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        if(menu.findItem(R.id.action_share)==null)
            inflater.inflate(R.menu.fragment_menu_2, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        intentShare.putExtra(Intent.EXTRA_TEXT, movieID + ": " + movieDescrip);
        mShareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu, inflater);
    }
}

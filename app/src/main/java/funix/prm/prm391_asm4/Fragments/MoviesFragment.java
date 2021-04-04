package funix.prm.prm391_asm4.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import funix.prm.prm391_asm4.Adapters.MoviesAdapter;
import funix.prm.prm391_asm4.Models.Movies;
import funix.prm.prm391_asm4.R;


public class MoviesFragment extends Fragment {

    private static final String URL = "https://api.androidhive.info/json/movies_2017.json";
    private RecyclerView mRecyclerView;
    private MoviesAdapter mAdapter;
    private ArrayList<Movies> mMoviesList;
    private RequestQueue mRequestQueue;


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_menu, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.singOut:
                Toast.makeText(getContext(), "Sign Out clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle(R.string.movies_text);

        View rootView = inflater.inflate(R.layout.fragment_movies, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycler_view);
        mMoviesList = new ArrayList<>();
        fetchMoviesItem();

        mAdapter = new MoviesAdapter(getActivity(), getContext(), mMoviesList,
                AccessToken.getCurrentAccessToken() != null);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setNestedScrollingEnabled(false);

        return rootView;
    }

    private void fetchMoviesItem() {
        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONArray>() {
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    // creating a new json object and
                    // getting each object from json array.
                    try {
                        JSONObject responseObj = response.getJSONObject(i);

                        String imgUrl = responseObj.getString("image");
                        String title = responseObj.getString("title");
                        String price = responseObj.getString("price");
                        Movies movie = new Movies(imgUrl, title, price);
                        Log.d("FetchMoviesItem", "onResponse: " + movie.toString());
                        mMoviesList.add(movie);
                        mAdapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Fail to get the data..", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonArrayRequest);
    }

}
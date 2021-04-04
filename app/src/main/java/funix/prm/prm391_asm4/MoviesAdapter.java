package funix.prm.prm391_asm4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private final Context mContext;
    private final ArrayList<Movies> mMovieList;

    public MoviesAdapter(Context context, ArrayList<Movies> movieList) {
        this.mContext = context;
        this.mMovieList = movieList;
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.recyclerview_item, parent, false);
        return new MoviesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movies movie = mMovieList.get(position);

        String imageUrl = movie.getmImageLink();
        String name = movie.getmMoviesName();
        String price = movie.getmMoviePrice();

        holder.mMoviesName.setText(name);
        holder.mMoviesPrice.setText(price);
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(holder.mMoviesImg);


    }


    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MoviesViewHolder extends
            RecyclerView.ViewHolder {

        public ImageView mMoviesImg;
        public TextView mMoviesName;
        public TextView mMoviesPrice;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            mMoviesImg = itemView.findViewById(R.id.movies_img);
            mMoviesName = itemView.findViewById(R.id.movies_name);
            mMoviesPrice = itemView.findViewById(R.id.movies_price);

        }
    }
}

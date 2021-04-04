package funix.prm.prm391_asm4;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    private final Activity mActivity;
    private final Context mContext;
    private final ArrayList<Movies> mMovieList;
    private final boolean mIsFacebookLoggedIn;


    public MoviesAdapter(Activity activity, Context context, ArrayList<Movies> movieList, boolean isFacebookLoggedIn) {
        this.mActivity = activity;
        this.mContext = context;
        this.mMovieList = movieList;
        this.mIsFacebookLoggedIn = isFacebookLoggedIn;
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

        holder.mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setMessage(mActivity.getResources().getString(R.string.facebook_share_dialog_text));
                builder.setCancelable(true);
                builder.setNegativeButton(mActivity.getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.cancel();
                    }
                });
                builder.setPositiveButton(mActivity.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Check if fb is login or not
                        if (mIsFacebookLoggedIn == true) {

                        }


                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


    }


    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    public class MoviesViewHolder extends
            RecyclerView.ViewHolder {
        public LinearLayout mRootView;
        public ImageView mMoviesImg;
        public TextView mMoviesName;
        public TextView mMoviesPrice;

        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            mRootView = itemView.findViewById(R.id.item_linearlayout);
            mMoviesImg = itemView.findViewById(R.id.movies_img);
            mMoviesName = itemView.findViewById(R.id.movies_name);
            mMoviesPrice = itemView.findViewById(R.id.movies_price);

        }
    }
}

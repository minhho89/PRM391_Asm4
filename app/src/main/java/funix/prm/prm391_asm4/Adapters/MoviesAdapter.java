package funix.prm.prm391_asm4.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import funix.prm.prm391_asm4.Models.Movies;
import funix.prm.prm391_asm4.R;

/**
 * Adapter for RecycleView showing movies grid in MovieFragment
 */
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

        String imageUrl = movie.getImageLink();
        String name = movie.getMoviesName();
        String price = movie.getMoviePrice();

        holder.mMoviesName.setText(name);
        holder.mMoviesPrice.setText(price);
        Picasso.get()
                .load(imageUrl)
                .fit()
                .centerInside()
                .into(holder.mMoviesImg);


        // When user clicked to a grid movie item, the apps would prompt
        // a dialog asking to share image to Facebook
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
                            // Initializes sharing dialog
                            Bitmap image = getBitmapFromURL(imageUrl);
                            SharePhoto photo = new SharePhoto.Builder()
                                    .setBitmap(image)
                                    .build();
                            SharePhotoContent content = new SharePhotoContent.Builder()
                                    .addPhoto(photo)
                                    .build();
                            ShareDialog shareDialog = new ShareDialog(mActivity);
                            shareDialog.show(content);
                        } else {
                            // TODO: set String value
                            Toast.makeText(mActivity,
                                    "Please sign in to your Facebook account first",
                                    Toast.LENGTH_SHORT).show();
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

    /**
     * Converts an image from its URL to Bitmap object
     * using for initializing Facebook sharing dialog
     *
     * @param imageUrl image URL
     * @return Bitmap Object
     */
    private Bitmap getBitmapFromURL(String imageUrl) {
        Bitmap image = null;
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            try {

                URL url = new URL(imageUrl);
                image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            } catch (IOException e) {
                System.out.println(e);
                Toast.makeText(mActivity, e + "", Toast.LENGTH_SHORT).show();
            }

        }
        return image;
    }


    public class MoviesViewHolder extends RecyclerView.ViewHolder {
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

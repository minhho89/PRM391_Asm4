package funix.prm.prm391_asm4.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.squareup.picasso.Picasso;

import funix.prm.prm391_asm4.R;

/**
 * Handles showing user's information
 */
public class ProfileFragment extends Fragment {

    private View mRootView;
    private ImageView mProfileImg;
    private TextView mUserNameTxt;
    private TextView mEmailTxt;
    private TextView mIdTxt;

    public ProfileFragment() {
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
        getActivity().setTitle(R.string.profile_text);
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mProfileImg = mRootView.findViewById(R.id.profile_image);
        mUserNameTxt = mRootView.findViewById(R.id.profile_name_txt);
        mEmailTxt = mRootView.findViewById(R.id.profile_email_txt);
        mIdTxt = mRootView.findViewById(R.id.profile_id_txt);

        // Retrieves Google profile data
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getActivity());
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            String personId = acct.getId();
            Uri personPhoto = acct.getPhotoUrl();

            Picasso.get().load(personPhoto).into(mProfileImg);
            mUserNameTxt.setText(personName);
            mEmailTxt.setText("Email: " + personEmail);
            mIdTxt.setText("User Id: " + personId);

            Log.d("GOOGLE", "onCreateView: email: " + personEmail);

        } else {
            // Get Facebook profile data from saved bundle
            Bundle bundle = this.getArguments();

            String imageURL = bundle.getString("imageURL");
            String userName = bundle.getString("name");
            String email = bundle.getString("email");
            String id = bundle.getString("id");

            Picasso.get().load(imageURL).into(mProfileImg);
            mUserNameTxt.setText(userName);
            mEmailTxt.setText("Email: " + email);
            mIdTxt.setText("User ID: " + id);
        }

        return mRootView;
    }
}
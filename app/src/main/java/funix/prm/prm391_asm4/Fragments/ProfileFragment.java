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
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.squareup.picasso.Picasso;

import funix.prm.prm391_asm4.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    View mRootView;
    ImageView mProfileImg;
    TextView mUserNameTxt;
    TextView mEmailTxt;
    TextView mIdTxt;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInOptions mGso;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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


        // Google
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
            // Facebook
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
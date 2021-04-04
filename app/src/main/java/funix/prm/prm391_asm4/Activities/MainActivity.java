package funix.prm.prm391_asm4.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import funix.prm.prm391_asm4.Fragments.MoviesFragment;
import funix.prm.prm391_asm4.Fragments.ProfileFragment;
import funix.prm.prm391_asm4.R;

/**
 * Handles main thread of the application.
 * Implements Navigation bar and its fragments activities
 */
public class MainActivity extends AppCompatActivity {
    private static MainActivity mInstance;
    private BottomNavigationView bottomNav;
    private RequestQueue mRequestQueue;

    public static synchronized MainActivity getInstance() {
        return mInstance;
    }

    /**
     * Handles Navigation options selected event
     */
    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        // Showing moveies fragment
                        case R.id.botNav_movies:
                            selectedFragment = new MoviesFragment();
                            break;

                        // Showing profile fragment
                        case R.id.botNav_profile:
                            Intent intent = getIntent();
                            Bundle b = intent.getExtras();

                            selectedFragment = new ProfileFragment();
                            selectedFragment.setArguments(b);
                            break;
                    }

                    // Change to selected fragment
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, selectedFragment)
                            .commit();

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mInstance = this;

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

//        mPref = getApplication().getSharedPreferences("Options", Context.MODE_PRIVATE);

        // Movie fragment loaded by default
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment,
                        new MoviesFragment())
                .commit();

    }

    /**
     * Handles logout button on Appbar
     *
     * @param item logout button
     * @return true if button clicked, otherwise false
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.singOut:
                logoutGoogle();
                logoutFacebook();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Facebook log out event
     */
    private void logoutFacebook() {

        // Check if user have logged in or not
        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(),
                "/me/permissions/",
                null,
                HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();
                Log.d("MainActivity", "onComplete: Facebook Sign out");

            }
        }).executeAsync();
    }

    /**
     * Google log out event
     */
    private void logoutGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Check if google have logged or not
        if (GoogleSignIn.getClient(this, gso) != null) {

            GoogleSignInClient mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
            mGoogleSignInClient.signOut()
                    .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Log.d("MainActivity", "onComplete: Google Sign out");
                            moveToLoginActivity();
                        }
                    });
        }
    }


    /**
     * After logged out, moves back to log in activity
     */
    private void moveToLoginActivity() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
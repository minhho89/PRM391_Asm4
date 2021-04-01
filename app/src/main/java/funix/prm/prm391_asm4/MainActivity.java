package funix.prm.prm391_asm4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNav;
    Intent receiveIntent;
    String mImageUrl;
    String mUserName;
    String mLink;
    String mEmail;
    SharedPreferences mPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        mPref = getApplication().getSharedPreferences("Options", Context.MODE_PRIVATE);

//        String id = mPref.getString("id","");
//        mImageUrl = mPref.getString("imageURL", "no url");
//        mUserName =mPref.getString("name", "");
//        mEmail = mPref.getString("email", "");
//        mLink = mPref.getString("link", "");

//        mImageUrl = getIntent().getExtras().getString("imageURL", "");
//        mUserName = getIntent().getExtras().getString("name", "");
////        mBirthday = getIntent().getExtras().getString("birthday", "");
//        mEmail = getIntent().getExtras().getString("email", "");



        // TODO: delete
//        Log.d("Main Activity: ", "___________");
//        Log.d("Main Activity: ", mImageUrl);
//        Log.d("Main Activity: ", mPref.getString("id","no id"));
//        Log.d("Main Activity: ", mPref.getString("name", ""));
//        Log.d("Main Activity: ", mPref.getString("email", ""));

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment,
                        new MoviesFragment())
                .commit();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.botNav_movies:
                            selectedFragment = new MoviesFragment();
                            break;
                        case R.id.botNav_profile:
//                            mImageUrl = getIntent().getExtras().getString("imageURL", "");
//                            mUserName = getIntent().getExtras().getString("name", "");
////                            mBirthday = getIntent().getExtras().getString("birthday", "");
//                            mEmail = getIntent().getExtras().getString("email1", "");
//                            //TODO: delete
//                            Toast.makeText(MainActivity.this, "MainActivity " + mEmail, Toast.LENGTH_SHORT).show();
//                            mLink = getIntent().getExtras().getString("link", "");

//                            Bundle b = new Bundle();
//                            b.putString("image_url", mImageUrl);
//                            b.putString("user_name", mUserName);
////                            b.putString("user_birthday", mBirthday);
//                            b.putString("user_email", mEmail);
//                            b.putString("user_link", mLink);
//

                            Intent intent = getIntent();
                            Bundle b = intent.getExtras();

                            selectedFragment = new ProfileFragment();
                            selectedFragment.setArguments(b);

                            break;
                    }

                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, selectedFragment)
                            .commit();

                    return true;
                }
            };

}
package funix.prm.prm391_asm4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private LoginButton mFbBtn;
    private CallbackManager mCallbackManager;
    private String mImageUrl = "";
    private String mId = "";
    private String mUserName = "";
    private String mEmail = "";
    private static final int RC_SIGN_IN = 100;
    private final String mLink = "";
    GoogleSignInClient mGoogleSignInClient;
    private SignInButton mGgBtn;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences("Options", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();


        // Facebook
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

        if (requestCode == RC_SIGN_IN) {
            // Google
            GoogleSignInResult result = Auth.GoogleSignInApi
                    .getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Google Log in failed", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Facebook
            Bundle b = new Bundle();

            GraphRequest request = GraphRequest.newMeRequest(
                    AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            try {
                                mId = object.getString("id");
                                mUserName = object.getString("name");
                                mImageUrl = "https://graph.facebook.com/"
                                        + AccessToken.getCurrentAccessToken().getUserId()
                                        + "/picture?width=400&height=400";


                                mEmail = object.getString("email");
                                Toast.makeText(getApplicationContext(), "Request " + object.toString(), Toast.LENGTH_SHORT).show();
//                            mLink = object.getString("link");
//                            Toast.makeText(getApplicationContext(), mLink, Toast.LENGTH_SHORT).show();

                                editor.putString("id", mId);
                                Log.d("demo", "onCompleted: " + mId);

                                editor.putString("imageURL", mImageUrl);
                                Log.d("demo", "onCompleted: url " + mImageUrl);
                                editor.putString("name", mUserName);
                                editor.putString("email", mEmail);


                                b.putString("id", mId);
                                b.putString("imageURL", mImageUrl);
                                b.putString("name", mUserName);
                                b.putString("email", mEmail);

                                intent.putExtras(b);
                                startActivity(intent);
                                finish();

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });


            b.putString("fields", "id,name,email");

            request.setParameters(b);
            request.executeAsync();

            editor.commit();
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        mFbBtn = findViewById(R.id.fb_login_button);
        mGgBtn = findViewById(R.id.g_login);
        mCallbackManager = CallbackManager.Factory.create();

        // Facebook
        mFbBtn.setPermissions(Arrays.asList("public_profile", "email"));


        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i("Facebook", "onSuccess: Log in success");
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                Log.i("ERROR", error.toString());
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Google
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        mGgBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.g_login:
                        googleSignIn();
                        break;
                }
            }
        });

    }

    private void googleSignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);

    }


}
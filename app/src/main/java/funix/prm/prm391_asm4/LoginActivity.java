package funix.prm.prm391_asm4;

import androidx.annotation.LongDef;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.ImageRequest;

import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private LoginButton mFbBtn;
    private CallbackManager mCallbackManager;
    private String mImageUrl = "";
    private String mId = "";
    private String mUserName = "";
    private String mBirthday = "";
    private String mEmail = "";
    private String mLink = "";
    private Profile mProfile;



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        SharedPreferences sharedPref = getApplicationContext()
                .getSharedPreferences("Options", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);

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

                            startActivity(intent);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        Bundle b = new Bundle();
        b.putString("fields", "id,name,email");

        request.setParameters(b);
        request.executeAsync();

        editor.commit();


//                intent.putExtra("imageURL", mImageUrl);
//                intent.putExtra("name", mUserName);
//                intent.putExtra("email", mEmail);
//                intent.putExtra("link", mLink);
//

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Facebook
        mFbBtn = findViewById(R.id.fb_login_button);
        mCallbackManager = CallbackManager.Factory.create();

        mFbBtn.setPermissions(Arrays.asList("public_profile", "email"));

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
//                mProfile = Profile.getCurrentProfile();
//                mUserName = mProfile.getName();
                // Get profile picture URL
//                mImageUrl = "https://graph.facebook.com/"
//                        + loginResult.getAccessToken().getUserId()
//                        + "/picture?width=400&height=400";
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

    }


    private void moveToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("imageURL", mImageUrl);
        intent.putExtra("name", mUserName);

        Bundle b = new Bundle();

        intent.putExtra("email", b.getBundle("email"));
        //TODO: delete
        Toast.makeText(getApplicationContext(), "thanh cong put email " + mEmail, Toast.LENGTH_SHORT).show();
        intent.putExtra("link", mLink);

        startActivity(intent);
    }
}
package funix.prm.prm391_asm4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private LoginButton mFbBtn;
    private CallbackManager mCallbackManager;
    private String mImageUrl = "";
    private String mUserName;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Facebook
        mFbBtn = findViewById(R.id.fb_login_button);
        mCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                mImageUrl = "https://graph.facebook.com/"
                        + loginResult.getAccessToken().getUserId()
                        + "/picture?width=300&height=300";
//                info.setText("User Id: " + loginResult.getAccessToken().getUserId());
//                String imageURL = "https://graph.facebook.com/"
//                        + loginResult.getAccessToken().getUserId()
//                        + "/picture?width=300&height=300";
//                Picasso.get().load(imageURL).into(profile);
                moveToMainActivity();
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
        startActivity(intent);
    }
}
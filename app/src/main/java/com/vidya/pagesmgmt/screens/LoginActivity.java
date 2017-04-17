package com.vidya.pagesmgmt.screens;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.vidya.pagesmgmt.R;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

private static final List<String> PERMISSIONS = Arrays.asList("manage_pages,publish_actions,publish_pages,read_insights");
    protected LoginButton loginButton;

    CallbackManager callbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login);

        loginButton = (LoginButton) findViewById(R.id.login_button);

        AccessToken accessToken = AccessToken.getCurrentAccessToken();

        if(accessToken!=null){
            Log.i("Access",accessToken.getToken());
            Intent intent = new Intent(this,HomeActivity.class);
            startActivity(intent);
        }


        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Intent intent = new Intent(LoginActivity.this,HomeActivity.class);
                startActivity(intent);
                Log.i("AcessToken",loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),R.string.login_cancelled,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                Log.i("error",exception.getMessage());
                Toast.makeText(getApplicationContext(),R.string.login_failed,Toast.LENGTH_LONG).show();
            }
        });

        if(loginButton.getFragment()!=null){
            LoginManager.getInstance().logInWithPublishPermissions(loginButton.getFragment(),PERMISSIONS);
        }else{
            LoginManager.getInstance().logInWithPublishPermissions(LoginActivity.this,PERMISSIONS);
        }
//
//        try {
//            PackageInfo info = getPackageManager().getPackageInfo(
//                    "com.vidya.pagesmgmt",
//                    PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//        } catch (NoSuchAlgorithmException e) {
//
//        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

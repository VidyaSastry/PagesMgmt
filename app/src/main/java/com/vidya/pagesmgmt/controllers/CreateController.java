package com.vidya.pagesmgmt.controllers;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.vidya.pagesmgmt.controllers.requests.AccessTokenGenerator;
import com.vidya.pagesmgmt.controllers.requests.Posts;

public class CreateController {

    public static final String PAGE_ID = "244621319339813";

    private void getAccessToken(AccessTokenGenerator.OnAccessTokenGenerated accessTokenGenerated){
        AccessTokenGenerator.newInstance(accessTokenGenerated).generate();
    }

    public void createPosts(final OnCreateListener a, final String message,final String params){
        getAccessToken(new AccessTokenGenerator.OnAccessTokenGenerated(){
            @Override
            public void onTokenSuccess(AccessToken accessToken) {
                Log.i("new", accessToken.getToken());
                Posts.newInstance(accessToken, new Posts.OnCreatePostsListener() {

                    @Override
                    public void onSuccess(String response) {
                        a.onCreateSucces(response);
                    }

                    @Override
                    public void onFailure(FacebookRequestError error) {
                        a.onFailure(error.getErrorMessage());
                    }

                },PAGE_ID, message, params).fetch();
            }

            @Override
            public void onTokenFailure(FacebookRequestError error) {
                a.onFailure(error.getErrorMessage());
            }
        });
    }

    public interface OnCreateListener {
        void onCreateSucces(String result);
        void onFailure(String message);
    }
}

package com.vidya.pagesmgmt.controllers;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.vidya.pagesmgmt.controllers.requests.AccessTokenGenerator;
import com.vidya.pagesmgmt.controllers.requests.PagePost;

public class PagePostController {

    private static final String PAGE_ID = "244621319339813";

    public static final String PUBLISH = "publish";
    public static final String SAVE = "save";

    private static void getAccessToken(AccessTokenGenerator.OnAccessTokenGenerated accessTokenGenerated){
        AccessTokenGenerator.newInstance(accessTokenGenerated).generate();
    }

    public static void createPosts(final OnCreateListener a, final String message,final String params){
        getAccessToken(new AccessTokenGenerator.OnAccessTokenGenerated(){
            @Override
            public void onTokenSuccess(AccessToken accessToken) {
                Log.i("new", accessToken.getToken());
                PagePost.newInstance(accessToken, new PagePost.OnCreatePostsListener() {

                    @Override
                    public void onSuccess(String response) {
                        a.onCreateSuccess(response);
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
        void onCreateSuccess(String result);
        void onFailure(String message);
    }
}

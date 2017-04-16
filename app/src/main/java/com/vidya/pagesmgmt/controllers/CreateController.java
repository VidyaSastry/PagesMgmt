package com.vidya.pagesmgmt.controllers;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.vidya.pagesmgmt.controllers.requests.AccessTokenGenerator;
import com.vidya.pagesmgmt.controllers.requests.Posts;

/**
 * Created by Vidya on 4/11/17.
 */

public class CreateController {

    public static final String PAGE_ID = "1138448119614327";

    private void getAccessToken(AccessTokenGenerator.OnAccessTokenGenerated accessTokenGenerated){
        AccessTokenGenerator.newInstance(accessTokenGenerated).generate();
    }

    public void createPosts(final OnCreateListener a, final String message,final String params){
        getAccessToken(new AccessTokenGenerator.OnAccessTokenGenerated(){
            @Override
            public void onSuccess(AccessToken accessToken) {
                new Posts(new Posts.OnCreatePostsListener() {
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
            public void onFailure(FacebookRequestError error) {
                a.onFailure(error.getErrorMessage());
            }
        });
    }

    public interface OnCreateListener {
        void onCreateSucces(String result);
        void onFailure(String message);
    }
}

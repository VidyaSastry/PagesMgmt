package com.vidya.pagesmgmt.controllers;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphResponse;
import com.vidya.pagesmgmt.controllers.requests.AccessTokenGenerator;
import com.vidya.pagesmgmt.controllers.requests.Feed;
import com.vidya.pagesmgmt.model.Post;

import org.json.JSONObject;

import java.util.List;


public class FeedController {

    private static final String PAGE_ID = "244621319339813";

    private static void getAccessToken(AccessTokenGenerator.OnAccessTokenGenerated accessTokenGenerated){
        AccessTokenGenerator.newInstance(accessTokenGenerated).generate();
    }

    public static void getFeed(final OnPostsFetchListener a){
        getAccessToken(new AccessTokenGenerator.OnAccessTokenGenerated() {
            @Override
            public void onTokenSuccess(AccessToken accessToken) {
                Log.i("AccessToken", "onSuccess" + accessToken.getToken());
                Feed.newInstance(accessToken,new Feed.OnFeedUpdateListener() {
                    @Override
                    public void onFeedSuccess(GraphResponse response) {
                        try {
                            JSONObject json = response.getJSONObject();
                            Log.i("Feed", "Json " + json.toString() );
                            a.onSuccess(Post.parseFeed(json.getJSONArray("data")));
                        } catch (Exception e){
                            a.onFailure("JSON parsing failed");
                        }
                    }

                    @Override
                    public void onFeedFailure(FacebookRequestError error) {
                        Log.i("Feed", "onFailure");
                        Log.i("Feed", error.getErrorMessage());
                        a.onFailure(error.getErrorMessage());
                    }
                }, PAGE_ID).fetch();
            }

            @Override
            public void onTokenFailure(FacebookRequestError error) {
                Log.i("AccessToken", "onFailure");
                Log.i("AccessToken", error.getErrorMessage());
            }
        });
    }


    
    public interface OnPostsFetchListener {
        void onSuccess(List<Post> postList);
        void onFailure(String message);
    }



}

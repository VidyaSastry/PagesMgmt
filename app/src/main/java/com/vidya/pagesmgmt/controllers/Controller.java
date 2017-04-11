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

/**
 * Created by Vidya on 4/10/17.
 */

public class Controller {

    public static final String PAGE_ID = "1138448119614327";

    private void getAccessToken(AccessTokenGenerator.OnAccessTokenGenerated accessTokenGenerated){
        AccessTokenGenerator.newInstance(accessTokenGenerated).generate();
    }

    public void getFeed(final OnPostsFetchListener a){
        getAccessToken(new AccessTokenGenerator.OnAccessTokenGenerated() {
            @Override
            public void onSuccess(AccessToken accessToken) {
                Log.i("AccessToken", "onSuccess" + accessToken.getToken());
                new Feed(new Feed.OnFeedUpdateListener() {
                    @Override
                    public void onSuccess(GraphResponse response) {
                        try {
                            JSONObject json = response.getJSONObject();
                            Log.i("Feed", "Json " + json.toString() );
                            a.onSuccess(Post.parseFeed(json.getJSONArray("data")));
                        } catch (Exception e){
                            a.onFailure("JSON parsing failed");
                        }
                    }

                    @Override
                    public void onFailure(FacebookRequestError error) {
                        Log.i("Feed", "onFailure");
                        Log.i("Feed", error.getErrorMessage());
                        a.onFailure(error.getErrorMessage());
                    }
                }, PAGE_ID).fetch();
            }

            @Override
            public void onFailure(FacebookRequestError error) {
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

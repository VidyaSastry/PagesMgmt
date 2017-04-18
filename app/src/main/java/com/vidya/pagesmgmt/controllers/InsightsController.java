package com.vidya.pagesmgmt.controllers;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.vidya.pagesmgmt.controllers.requests.AccessTokenGenerator;
import com.vidya.pagesmgmt.controllers.requests.ViewCount;


public class InsightsController {

    private static final String PAGE_ID = "244621319339813";

    private static void getAccessToken(AccessTokenGenerator.OnAccessTokenGenerated accessTokenGenerated){
        AccessTokenGenerator.newInstance(accessTokenGenerated).generate();
    }

    public static void getInsights(final String postId, final OnInsightsFetchedListener a){
        getAccessToken(new AccessTokenGenerator.OnAccessTokenGenerated() {
            @Override
            public void onTokenSuccess(AccessToken accessToken) {
                Log.i("AccessToken", "onSuccess" + accessToken.getToken());
                ViewCount.newInstance(accessToken,
                        new ViewCount.OnReadyListener() {

                    @Override
                    public void onSuccess(Integer viewCount) {
                        a.onSuccess(viewCount);
                    }

                    @Override
                    public void onFailure(FacebookRequestError error) {
                        Log.i("Feed", "onFailure");
                        Log.i("Feed", error.getErrorMessage());
                        a.onFailure(error.getErrorMessage());
                    }
                }, postId).fetch();
            }

            @Override
            public void onTokenFailure(FacebookRequestError error) {
                Log.i("AccessToken", "onFailure");
                Log.i("AccessToken", error.getErrorMessage());
            }
        });
    }


    
    public interface OnInsightsFetchedListener {
        void onSuccess(Integer postViewCount);
        void onFailure(String message);
    }



}

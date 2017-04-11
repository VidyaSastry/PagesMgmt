package com.vidya.pagesmgmt.controllers.requests;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

/**
 * Created by Vidya on 4/10/17.
 */

public class Feed {


    private GraphRequest graphRequest;

    public Feed(final OnFeedUpdateListener a, String pageId) {
        this.graphRequest
                = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+pageId+"/feed",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if(response.getError() == null) {
                            a.onSuccess(response);
                        } else a.onFailure(response.getError());

                    }
                }
        );
    }

    public static Feed newInstance(OnFeedUpdateListener a, String pageId) {
        return new Feed(a, pageId);
    }

    public void fetch(){
        graphRequest.executeAsync();
    }

    public interface OnFeedUpdateListener {
        public void onSuccess(GraphResponse response);
        public void onFailure(FacebookRequestError error);
    }
}
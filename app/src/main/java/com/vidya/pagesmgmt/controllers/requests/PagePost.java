package com.vidya.pagesmgmt.controllers.requests;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import static com.vidya.pagesmgmt.controllers.PagePostController.SAVE;


public class PagePost {

    private GraphRequest graphRequest;

    public PagePost(AccessToken accessToken, final OnCreatePostsListener a, String pageId, String message, final String p){
        Bundle params = new Bundle();
        params.putString("message",message);

        if(p.equals(SAVE)){
            params.putString("published", "false");
//            params.putSerializable("unpublished_content_type", "DRAFT");
        }


        this.graphRequest
                = new GraphRequest(
                accessToken,
                "/"+pageId+"/feed",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback(){

                    @Override
                    public void onCompleted(GraphResponse response) {
                        if(response.getError()==null){
                            if(!p.equals(SAVE))
                                a.onSuccess("Published");
                            else
                                a.onSuccess("Saved");
                        }else{
                            a.onFailure(response.getError());
                        }
                    }
                }

        );
    }

    public static PagePost newInstance(AccessToken accessToken, OnCreatePostsListener a, String pageId, String message, String params) {
        return new PagePost(accessToken, a,pageId,message,params);
    }

    public void fetch(){
        graphRequest.executeAsync();
    }

    public interface OnCreatePostsListener{
        void onSuccess(String response);
        void onFailure(FacebookRequestError error);

    }
}

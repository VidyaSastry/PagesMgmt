package com.vidya.pagesmgmt.controllers.requests;

import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

/**
 * Created by Vidya on 4/10/17.
 */

public class Posts {

    private GraphRequest graphRequest;

    public Posts(final OnCreatePostsListener a, String pageId, String message, final String p){
        Bundle params = new Bundle();
        params.putString("message",message);

        if(p.equals("save")){
            params.putString("published", "false");
            params.putSerializable("unpublished_content_type", "DRAFT");
        }


        this.graphRequest
                = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/"+pageId+"/feed",
                params,
                HttpMethod.POST,
                new GraphRequest.Callback(){

                    @Override
                    public void onCompleted(GraphResponse response) {
                        if(response.getError()==null){
                            if(!p.equals("save"))
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

    public static Posts newInstance(OnCreatePostsListener a, String pageId,String message,String params) {
        return new Posts(a,pageId,message,params);
    }

    public void fetch(){
        graphRequest.executeAsync();
    }

    public interface OnCreatePostsListener{
        public void onSuccess(String response);
        public void onFailure(FacebookRequestError error);

    }
}

package com.vidya.pagesmgmt.controllers.requests;

import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.FacebookRequestError;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Insight {

    private GraphRequest graphRequest;

    public Insight(AccessToken accessToken, final OnReadyListener a, String postId){

        this.graphRequest
                = new GraphRequest(
                accessToken,
                "/"+postId+"/insights/post_impressions",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback(){

                    @Override
                    public void onCompleted(GraphResponse response) {
                        if(response.getError()==null){
                            try {
                                JSONObject json = response.getJSONObject();
                                Log.i(Insight.this.getClass().getSimpleName(),
                                        json.toString());
                                JSONArray jsonArray = json.getJSONArray("data");
                                JSONObject dayViews = jsonArray.getJSONObject(0);
                                JSONArray jsonArrayValues = dayViews.getJSONArray("values");
                                JSONObject lastJson = jsonArrayValues.getJSONObject(jsonArrayValues.length() - 1);
                                a.onSuccess(lastJson.getInt("value"));
                            } catch (JSONException e){
                                a.onFailure(response.getError());
                            }
                        }else{
                            a.onFailure(response.getError());
                        }
                    }
                }

        );
    }

    public static Insight newInstance(AccessToken accessToken, OnReadyListener listener, String postId) {
        return new Insight(accessToken, listener, postId);
    }

    public void fetch(){
        graphRequest.executeAsync();
    }

    public interface OnReadyListener {
        void onSuccess(Integer viewCount);
        void onFailure(FacebookRequestError error);

    }
}

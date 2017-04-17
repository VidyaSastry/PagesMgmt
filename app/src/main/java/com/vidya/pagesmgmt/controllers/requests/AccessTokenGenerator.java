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


public class AccessTokenGenerator {


    private GraphRequest graphRequest;

    public AccessTokenGenerator(final OnAccessTokenGenerated a) {
        this.graphRequest = new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/me/accounts",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if(response.getError() == null) {
                            JSONObject res = response.getJSONObject();
                            try {

                                JSONArray data = res.getJSONArray("data");
                                String accessToken = data.getJSONObject(0).getString("access_token");
                                AccessToken currentToken = AccessToken.getCurrentAccessToken();
                                AccessToken new_access_token = new AccessToken(accessToken, currentToken.getApplicationId(),
                                        currentToken.getUserId(), currentToken.getPermissions(),
                                        currentToken.getDeclinedPermissions(), currentToken.getSource(),
                                        currentToken.getExpires(), currentToken.getLastRefresh());
                                a.onTokenSuccess(new_access_token);
                            } catch (JSONException e){
                                Log.e(this.getClass().getSimpleName(), e.getMessage());
                            }
                        }
                        else a.onTokenFailure(response.getError());
                    }
                }
        );
    }

    public static AccessTokenGenerator newInstance(OnAccessTokenGenerated a) {
        return new AccessTokenGenerator(a);
    }

    public void generate(){
        graphRequest.executeAsync();
    }

    public interface OnAccessTokenGenerated {
        void onTokenSuccess(AccessToken accessToken);
        void onTokenFailure(FacebookRequestError error);
    }
}

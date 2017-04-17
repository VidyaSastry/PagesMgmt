package com.vidya.pagesmgmt.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Vidya on 4/10/17.
 */

public class Post {

    public static final String MESSAGE = "message";
    public static final String ID = "id";
    public static final String CREATED_TIME = "created_time";
    public static final String IS_PUBLISHED="is_published";

    private String id;
    private String message;
    private Date date;
    private boolean isPublished;

    public static List<Post> parseFeed(JSONArray array) throws Exception {
        List<Post> posts = new ArrayList<>(array.length());

        for (int i = 0; i < array.length(); i++) {
            posts.add(new Post(array.getJSONObject(i)));
        }

        return posts;
    }

    public Post(JSONObject object) throws JSONException, ParseException {
        if (object.has(ID)) this.setId(object.getString(ID));
        if (object.has(CREATED_TIME)) this.setDate(object.getString(CREATED_TIME));
        if (object.has(MESSAGE)) this.setMessage(object.getString(MESSAGE));
        if (object.has(IS_PUBLISHED)) this.setPublished(object.getBoolean(IS_PUBLISHED));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isPublished() {
        return isPublished;
    }

    public void setPublished(boolean published) {
        isPublished = published;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(String dateString) throws ParseException {
        //"2017-04-09T01:21:20+0000"
        //"2001-07-04T12:08:56.235-0700
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault());
        this.date = sdf.parse(dateString);
    }
}

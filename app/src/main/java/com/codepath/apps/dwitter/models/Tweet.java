package com.codepath.apps.dwitter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lee on 8/4/16.
 */
//purpose: parse json + store the data
public class Tweet {
    public String getBody() {
        return body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public long getUid() {
        return uid;
    }

    public User getUser() {
        return user;
    }

    //1) list attributes
    private String body;
    private long uid; //unique id, not user_id
    private User user;
    private String createdAt;

    public static long getMaxId() {
        return maxId;
    }

    public static void setMaxId(long maxId) {
        Tweet.maxId = maxId;
    }

    public static void resetMaxId() {
        Tweet.maxId = 0;
    }

    private static long maxId = 0;

    //2) deserialize
    //Tweet.fromJson(...) => Tweet
    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            if(Tweet.maxId == 0 || tweet.uid < Tweet.maxId) {
                Tweet.setMaxId(--tweet.uid);
            }
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        for(int i=0; i<jsonArray.length(); i++) {
            JSONObject tweetJson = null;
            try {
                tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if(tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }
}

package com.codepath.apps.dwitter.models;

import android.util.Log;

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

    public boolean isFavorited() {
        return favorited;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    //1) list attributes
    private String body;
    private long uid; //unique id, not user_id

    private User user;

    private String createdAt;
    private boolean retweeted;
    private boolean favorited;
    private int retweetCount;
    private int favoriteCount;

    private String imageUrl;

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
            tweet.retweeted = jsonObject.getBoolean("retweeted");
            tweet.retweetCount = jsonObject.getInt("retweet_count");
            tweet.favorited = jsonObject.getBoolean("favorited");
            tweet.favoriteCount = jsonObject.getInt("favorite_count");
//            JSONArray jsonMedia = jsonObject.getJSONObject("entities").getJSONArray("media");
//            for(int i=0; i < jsonMedia.length(); i++) {
//                tweet.imageUrl = jsonMedia.getJSONObject(i).getString("media_url");
//            }


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

package com.codepath.apps.dwitter.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by lee on 8/4/16.
 */
@Parcel
public class User {
    public String getName() {
        return name;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public int getFollowingsCount() {
        return followingsCount;
    }

    public String getTagline() {
        return tagline;
    }

    String name;
    long uid;
    String screenName;
    String profileImageUrl;
    String tagline;
    int followersCount;
    int followingsCount;

    public User() {

    }

    public static User fromJSON(JSONObject json) {
        User u = new User();
        try {
            u.name = json.getString("name");
            u.uid = json.getLong("id");
            u.screenName = json.getString("screen_name");
            u.profileImageUrl = json.getString("profile_image_url").replace("normal", "bigger");
            u.tagline = json.getString("description");
            u.followersCount = json.getInt("followers_count");
            u.followingsCount = json.getInt("friends_count");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
}

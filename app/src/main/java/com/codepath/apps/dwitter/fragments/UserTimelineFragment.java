package com.codepath.apps.dwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.codepath.apps.dwitter.TwitterApplication;
import com.codepath.apps.dwitter.TwitterClient;
import com.codepath.apps.dwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by lee on 8/11/16.
 */
public class UserTimelineFragment extends TweetsListFragment {
    private TwitterClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        client = TwitterApplication.getRestClient();
        populateTimeline(1);
    }

    public static UserTimelineFragment newInstance(String screenName) {
        UserTimelineFragment userFragment = new UserTimelineFragment();
        Bundle args = new Bundle();
        args.putString("screenName", screenName);
        userFragment.setArguments(args);
        return userFragment;
    }

    public void populateTimeline(int page) {
        String screenName = getArguments().getString("screenName");
        client.getUserTimeline(page, screenName, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJSONArray(response));
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }
}

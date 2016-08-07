package com.codepath.apps.dwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.codepath.apps.dwitter.R;
import com.codepath.apps.dwitter.TweetsArrayAdapter;
import com.codepath.apps.dwitter.TwitterApplication;
import com.codepath.apps.dwitter.TwitterClient;
import com.codepath.apps.dwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {
    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    SwipeRefreshLayout swipeContainer;
    RecyclerView rvTweets;

    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupToolbar();
        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);
        //create arraylist (data source)
        tweets = new ArrayList<>();
        //construct the adapter from data source
        aTweets = new TweetsArrayAdapter(this, tweets);
        //connect adapter to list view
        rvTweets.setAdapter(aTweets);

        rvTweets.setLayoutManager(new LinearLayoutManager(this));

        client = TwitterApplication.getRestClient();
        populateTimeline();

        setupEndlessScroll();
        setupSwipeRefresh();
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbarTimeline);
    }

    public void setupEndlessScroll() {
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
//        rvTweets.setOnScrollListener(new EndlessScrollListener() {
//            @Override
//            public boolean onLoadMore(int page, int totalItemsCount) {
//                populateTimeline();
//                return true;
//            }
//        });
    }

    public void setupSwipeRefresh() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                tweets.clear();
                aTweets.notifyDataSetChanged();
                Tweet.resetMaxId();
                populateTimeline();
                swipeContainer.setRefreshing(false);
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    public void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                tweets.addAll(Tweet.fromJSONArray(response));
                aTweets.notifyDataSetChanged();
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void newTweet(MenuItem mi) {
        Intent i = new Intent(this, NewTweetActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    public void newTweet(View view) {
        Intent i = new Intent(this, NewTweetActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String newBody = data.getStringExtra("body");
            client.createTweet(newBody, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Tweet newTweet = Tweet.fromJSON(response);
                    tweets.add(0, newTweet);
                    aTweets.notifyItemInserted(0);
                    rvTweets.scrollToPosition(0);
                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("fail", errorResponse.toString());
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

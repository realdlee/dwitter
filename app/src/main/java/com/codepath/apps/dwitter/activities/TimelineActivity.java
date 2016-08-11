package com.codepath.apps.dwitter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import com.codepath.apps.dwitter.R;

public class TimelineActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupToolbar();

//unnecessary - removed at 20:00 in Walkthrough
//        if (savedInstanceState == null) {
//            fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
//        }
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setLogo(R.drawable.twitter_white);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbarTimeline);
    }

//    public void newTweet(MenuItem mi) {
//        Intent i = new Intent(this, NewTweetActivity.class);
//        startActivityForResult(i, REQUEST_CODE);
//    }
//
//    public void newTweet(View view) {
//        Intent i = new Intent(this, NewTweetActivity.class);
//        startActivityForResult(i, REQUEST_CODE);
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
//            String newBody = data.getStringExtra("body");
//            client.createTweet(newBody, new JsonHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                    Tweet newTweet = Tweet.fromJSON(response);
////                    tweets.add(0, newTweet);
////                    aTweets.notifyItemInserted(0);
////                    rvTweets.scrollToPosition(0);
//                    super.onSuccess(statusCode, headers, response);
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                    Log.e("fail", errorResponse.toString());
//                    super.onFailure(statusCode, headers, throwable, errorResponse);
//                }
//            });
//        }
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

package com.codepath.apps.dwitter.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.dwitter.R;
import com.codepath.apps.dwitter.TwitterApplication;
import com.codepath.apps.dwitter.TwitterClient;
import com.codepath.apps.dwitter.UsersArrayAdapter;
import com.codepath.apps.dwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class UserActivity extends AppCompatActivity {
    TwitterClient client;
    TextView mTitle;
    private ArrayList<User> users;
    private UsersArrayAdapter aUsers;
    ListView lvUsers;
    String userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        userType = getIntent().getStringExtra("user_type");
        setupToolbar();
        client = TwitterApplication.getRestClient();

        String screenName = getIntent().getStringExtra("screen_name");
        lvUsers = (ListView) findViewById(R.id.lvUsers);
        users = new ArrayList<>();
        aUsers = new UsersArrayAdapter(this, users);
        lvUsers.setAdapter(aUsers);
        if(userType.equals("followers")) {
            client.getFollowers(1, screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        aUsers.clear();
                        JSONArray jsonUsers = response.getJSONArray("users");
                        users = User.fromJSONArray(jsonUsers);
                        aUsers.addAll(users);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("error", errorResponse.toString());
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        } else {
            client.getFriends(1, screenName, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        aUsers.clear();
                        JSONArray jsonUsers = response.getJSONArray("users");
                        users = User.fromJSONArray(jsonUsers);
                        aUsers.addAll(users);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("error", errorResponse.toString());
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(userType);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

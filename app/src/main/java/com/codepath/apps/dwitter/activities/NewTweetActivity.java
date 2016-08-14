package com.codepath.apps.dwitter.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.dwitter.R;
import com.codepath.apps.dwitter.TwitterApplication;
import com.codepath.apps.dwitter.TwitterClient;
import com.codepath.apps.dwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class NewTweetActivity extends AppCompatActivity {
    @BindView(R.id.etNewTweet)
    EditText etNewTweet;
    @BindView(R.id.tvCharactersRemaining)
    TextView tvCharactersRemaining;
    Integer MAX_BODY_LENGTH = 140;
    TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);
        setupToolbar();
        ButterKnife.bind(this);
        client = TwitterApplication.getRestClient();
        etNewTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int charRemaining = MAX_BODY_LENGTH - editable.toString().length();
                if (charRemaining == 0) {
                    tvCharactersRemaining.setTextColor(Color.RED);
                } else if (charRemaining <= 10) {
                    tvCharactersRemaining.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
                }
                tvCharactersRemaining.setText(Integer.toString(charRemaining));
            }
        });
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        mTitle.setText(R.string.toolbarNewTweet);
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

    @OnClick(R.id.btnSubmit)
    public void submitTweet(View view) {
        if (etNewTweet.length() == 0) {
            Toast.makeText(this, "Please type a tweet!", Toast.LENGTH_SHORT).show();
        } else if (etNewTweet.length() > MAX_BODY_LENGTH) {
            Toast.makeText(this, "Your tweet is over the maximum " + Integer.toString(MAX_BODY_LENGTH) + " characters allowed.", Toast.LENGTH_SHORT).show();
        } else {
            String newBody = etNewTweet.getText().toString();
            client.createTweet(newBody, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    Tweet newTweet = Tweet.fromJSON(response);
                    Intent i = new Intent();
                    i.putExtra("tweet", Parcels.wrap(newTweet));
                    setResult(RESULT_OK, i);
                    finish();
//                    super.onSuccess(statusCode, headers, response);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("fail", errorResponse.toString());
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }
            });
        }
    }
}
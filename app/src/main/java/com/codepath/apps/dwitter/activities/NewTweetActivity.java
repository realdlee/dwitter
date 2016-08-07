package com.codepath.apps.dwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.dwitter.R;

public class NewTweetActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // This is the up button
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void submitTweet(View view) {
        TextView etNewTweet = (TextView) findViewById(R.id.etNewTweet);
        if(etNewTweet.length() == 0) {
            Toast.makeText(this, "Please type a tweet!", Toast.LENGTH_SHORT).show();
        } else if (etNewTweet.length() > 160) {
            Toast.makeText(this, "Your tweet is over the maximum 160 characters allowed.", Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent();
            i.putExtra("body", etNewTweet.getText().toString());
            setResult(RESULT_OK, i);
            finish();
        }
    }
}

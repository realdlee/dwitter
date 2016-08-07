package com.codepath.apps.dwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.dwitter.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTweetActivity extends AppCompatActivity {
    @BindView(R.id.etNewTweet) EditText etNewTweet;
    @BindView(R.id.tvCharactersRemaining) TextView tvCharactersRemaining;
    Integer MAX_BODY_LENGTH = 160;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_tweet);
        setupToolbar();
        ButterKnife.bind(this);
        etNewTweet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String charactersRemaining = Integer.toString(MAX_BODY_LENGTH - editable.toString().length());
                tvCharactersRemaining.setText(charactersRemaining);
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

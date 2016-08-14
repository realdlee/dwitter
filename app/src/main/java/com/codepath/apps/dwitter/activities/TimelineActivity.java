package com.codepath.apps.dwitter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.dwitter.R;
import com.codepath.apps.dwitter.SmartFragmentStatePagerAdapter;
import com.codepath.apps.dwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.dwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.dwitter.models.Tweet;

import org.parceler.Parcels;

public class TimelineActivity extends AppCompatActivity {
    private final int REQUEST_CODE = 20;
    private TweetsPagerAdapter adapterViewPager;
    HomeTimelineFragment fragmentHomeTimeline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        setupToolbar();

        //get viewpager
        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        //set viewpager adapter for the pager
        adapterViewPager = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        //find sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        //attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);

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

    public void onProfileView(MenuItem mi) {
        Intent i = new Intent(this, ProfileActivity.class);
        i.putExtra("screen_name", "launchfund");
        startActivity(i);
    }

    //returns the order of the fragments in the view pager
    public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {
        final int PAGE_COUNT = 2;
        private String tabTitles[] = {"Home", "Mentions"};

        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                fragmentHomeTimeline = new HomeTimelineFragment();
                return fragmentHomeTimeline;
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }

    public void newTweet(MenuItem mi) {
        Intent i = new Intent(this, NewTweetActivity.class);
        startActivityForResult(i, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            Tweet tweet = (Tweet) Parcels.unwrap(data.getParcelableExtra("tweet"));
            fragmentHomeTimeline = (HomeTimelineFragment) adapterViewPager.getRegisteredFragment(0);
            fragmentHomeTimeline.add(tweet);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

package com.codepath.apps.dwitter.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.dwitter.R;
import com.codepath.apps.dwitter.TweetsArrayAdapter;
import com.codepath.apps.dwitter.models.EndlessScrollListener;
import com.codepath.apps.dwitter.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lee on 8/10/16.
 */
public abstract class TweetsListFragment extends Fragment {
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private SwipeRefreshLayout swipeContainer;
    private final int REQUEST_CODE = 20;
//    private RecyclerView rvTweets;
    ListView lvTweets;

    //inflation logic
//    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, parent, false);

        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
//        //connect adapter to list view
        lvTweets.setAdapter(aTweets);

//        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweets);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        rvTweets.setLayoutManager(linearLayoutManager);
//
//        rvTweets.setAdapter(aTweets);
//        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
//        setupEndlessScroll(linearLayoutManager);

        lvTweets.setOnScrollListener(new EndlessScrollListener() {

            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                populateTimeline(page);
                return true; // ONLY if more data is actually being loaded; false otherwise.
            }
        });
//        setupSwipeRefresh();

        return v;
    }

    //creation lifecycle event
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //create arraylist (data source)
        tweets = new ArrayList<>();
// ;
        //construct the adapter from data source
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);
    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
//        tweets.addAll(tweets);
//        aTweets.notifyDataSetChanged();
    }

    public void add(Tweet tweet) {
        aTweets.add(tweet);
    }

    protected abstract void populateTimeline(int page);

//    public void setupSwipeRefresh() {
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                tweets.clear();
//                aTweets.notifyDataSetChanged();
//                Tweet.resetMaxId();
//                populateTimeline();
//                swipeContainer.setRefreshing(false);
//            }
//        });
//
//        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//    }
//
//    public void setupEndlessScroll(LinearLayoutManager linearLayoutManager) {
//        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount) {
//                populateTimeline();
//            }
//        });
//    }

//    public Boolean isNetworkAvailable() {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
//    }
//
//    public boolean isOnline() {
//        Runtime runtime = Runtime.getRuntime();
//        try {
//            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
//            int exitValue = ipProcess.waitFor();
//            return (exitValue == 0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

//    public TweetsArrayAdapter getAdapter() {
//        return aTweets;
//    }
}

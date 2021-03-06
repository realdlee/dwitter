package com.codepath.apps.dwitter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.dwitter.activities.ProfileActivity;
import com.codepath.apps.dwitter.models.Tweet;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.ParseException;
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by lee on 8/4/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        1) get tweet
        Tweet tweet = getItem(position);
//        2) find/inflate the template
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
//        3) find the subview to fill with data
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvRelativeTimestamp = (TextView) convertView.findViewById(R.id.tvRelativeTimestamp);

//        4) populate data into subview
        tvUserName.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvRelativeTimestamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext())
                .load(tweet.getUser().getProfileImageUrl())
                .transform(new RoundedCornersTransformation(3, 3))
                .into(ivProfileImage);
//        5) return view to be inserted
        ivProfileImage.setTag(position);
        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                Tweet tweet = getItem(position);
                Intent i = new Intent(getContext(), ProfileActivity.class);
                i.putExtra("screen_name", tweet.getUser().getScreenName());
                getContext().startActivity(i);
            }
        });
        return convertView;
    }

    public String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = 0;
            try {
                dateMillis = sf.parse(rawJsonDate).getTime();
            } catch (java.text.ParseException e) {
                e.printStackTrace();
            }
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }
}
//public class TweetsArrayAdapter extends RecyclerView.Adapter<TweetsArrayAdapter.ViewHolder> {
//    private List<Tweet> mTweets;
//    private Context mContext;
//
//    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
//        mTweets = tweets;
//        mContext = context;
//    }
//
//    private Context getContext() {
//        return mContext;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View tweetView = inflater.inflate(R.layout.item_tweet, parent, false);
//
//        ViewHolder viewHolder = new ViewHolder(tweetView);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        Tweet tweet = mTweets.get(position);
//        viewHolder.tvUserName.setText(tweet.getUser().getName());
//        viewHolder.tvScreenName.setText("@" + tweet.getUser().getScreenName());
//        viewHolder.tvBody.setText(tweet.getBody());
//        viewHolder.tvRelativeTimestamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
//        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
//        Glide
//                .with(getContext())
//                .load(tweet.getUser().getProfileImageUrl())
//                .placeholder(R.drawable.logo)
//                .centerCrop()
//                .into(viewHolder.ivProfileImage);
//
//        viewHolder.ivTweetImage.setImageResource(android.R.color.transparent);
//        if(tweet.getImageUrl() != null) {
//            Glide
//                    .with(getContext())
//                    .load(tweet.getImageUrl())
//                    .placeholder(R.drawable.logo)
//                    .centerCrop()
//                    .into(viewHolder.ivTweetImage);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return mTweets.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder {
//        public TextView tvUserName;
//        public ImageView ivProfileImage;
//        public ImageView ivTweetImage;
//        public TextView tvScreenName;
//        public TextView tvBody;
//        public TextView tvRelativeTimestamp;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            tvUserName = (TextView) itemView.findViewById(R.id.tvUserName);
//            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
//            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
//            tvRelativeTimestamp = (TextView) itemView.findViewById(R.id.tvRelativeTimestamp);
//            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
//            ivTweetImage = (ImageView) itemView.findViewById(R.id.ivTweetImage);
//        }
//    }
//}

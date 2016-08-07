package com.codepath.apps.dwitter;

import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.dwitter.models.Tweet;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.ParseException;

/**
 * Created by lee on 8/4/16.
 */
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {
    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    private static class ViewHolder {
        TextView tvUserName;
        ImageView ivProfileImage;
        TextView tvScreenName;
        TextView tvBody;
        TextView tvRelativeTimestamp;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        1) get tweet
        Tweet tweet = getItem(position);
//        2) find/inflate the template
        ViewHolder viewHolder;
//        3) find the subview to fill with data
        if(convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
            viewHolder.tvBody = (TextView) convertView.findViewById(R.id.tvBody);
            viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
            viewHolder.tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
            viewHolder.tvRelativeTimestamp = (TextView) convertView.findViewById(R.id.tvRelativeTimestamp);
            viewHolder.ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        4) populate data into subview
        viewHolder.tvUserName.setText(tweet.getUser().getName());
        viewHolder.tvScreenName.setText("@" + tweet.getUser().getScreenName());
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.tvRelativeTimestamp.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);
        Glide.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
//        5) return view to be inserted
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

package com.codepath.apps.dwitter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.dwitter.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import jp.wasabeef.picasso.transformations.RoundedCornersTransformation;

/**
 * Created by lee on 8/14/16.
 */
public class UsersArrayAdapter extends ArrayAdapter<User> {
    public UsersArrayAdapter(Context context, List<User> users) {
        super(context, android.R.layout.simple_list_item_1, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        1) get tweet
        User user = getItem(position);
//        2) find/inflate the template
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_user, parent, false);
        }
//        3) find the subview to fill with data
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);
        TextView tvTagline = (TextView) convertView.findViewById(R.id.tvTagline);

        tvUserName.setText(user.getName());
        tvScreenName.setText("@" + user.getScreenName());
        tvTagline.setText(user.getTagline());
        ivProfileImage.setImageResource(android.R.color.transparent);
        Picasso.with(getContext())
                .load(user.getProfileImageUrl())
                .transform(new RoundedCornersTransformation(3, 3))
                .into(ivProfileImage);

        return convertView;
    }
}

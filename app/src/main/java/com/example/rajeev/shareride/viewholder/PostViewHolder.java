package com.example.rajeev.shareride.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rajeev.shareride.R;
import com.example.rajeev.shareride.models.Post;

/**
 * Created by rajeev on 4/3/18.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public TextView mDateView;
    public TextView mTimeView;
//    public ImageView starView;
//    public TextView numStarsView;
//    public TextView bodyView;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.post_title);
        authorView = itemView.findViewById(R.id.post_author);
        mDateView = itemView.findViewById(R.id.text_date);
        mTimeView = itemView.findViewById(R.id.text_time);
//        starView = itemView.findViewById(R.id.star);
//        numStarsView = itemView.findViewById(R.id.post_num_stars);
//        bodyView = itemView.findViewById(R.id.post_body);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        mDateView.setText(post.dateOfJourney);
        mTimeView.setText(post.timeOfJourney);
//        authorView.setText(post.author);
//        numStarsView.setText(String.valueOf(post.starCount));
//        bodyView.setText(post.body);

//        starView.setOnClickListener(starClickListener);
    }
}

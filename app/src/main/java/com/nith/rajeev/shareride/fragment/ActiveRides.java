package com.nith.rajeev.shareride.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by rajeev on 4/3/18.
 */

public class ActiveRides extends PostListFragment {

    public ActiveRides() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(1000);

        return recentPostsQuery;
    }
}
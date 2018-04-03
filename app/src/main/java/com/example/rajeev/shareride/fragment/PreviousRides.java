package com.example.rajeev.shareride.fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

/**
 * Created by rajeev on 4/3/18.
 */

public class PreviousRides extends PostListFragment {

    public PreviousRides() {}

    @Override
    public Query getQuery(DatabaseReference databaseReference) {
        // All my posts
        return databaseReference.child("user-posts")
                .child(getUid());
    }
}

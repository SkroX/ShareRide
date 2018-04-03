package com.example.rajeev.shareride.models;

/**
 * Created by rajeev on 4/3/18.
 */

public class Comment {
    public String uid;
    public String author;
    public String text;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String author, String text) {
        this.uid = uid;
        this.author = author;
        this.text = text;
    }

}

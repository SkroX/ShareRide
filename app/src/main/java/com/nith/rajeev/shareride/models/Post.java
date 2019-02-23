package com.nith.rajeev.shareride.models;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rajeev on 4/3/18.
 */

public class Post{
        public String uid;
        public String author;
        public String title;
        public String dateOfJourney;
        public String timeOfJourney;
        public String body;

        public Post() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post(String uid, String author, String title, String dateOfJourney, String timeOfJourney, String body) {
            this.uid = uid;
            this.author = author;
            this.title = title;
            this.dateOfJourney = dateOfJourney;
            this.timeOfJourney = timeOfJourney;
            this.body = body;
        }

        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("uid", uid);
            result.put("author", author);
            result.put("title", title);
            result.put("dateOfJourney", dateOfJourney);
            result.put("timeOfJourney", timeOfJourney);
            result.put("body", body);

            return result;
        }
}

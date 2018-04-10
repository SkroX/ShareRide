package com.example.rajeev.shareride.models;

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
//        public String source;
//        public String destination;
        public String dateOfJourney;
        public String timeOfJourney;
        public String body;
//        public int starCount = 0;
//        public Map<String, Boolean> stars = new HashMap<>();

        public Post() {
            // Default constructor required for calls to DataSnapshot.getValue(Post.class)
        }

        public Post(String uid, String author, String title, String dateOfJourney, String timeOfJourney, String body) {
            this.uid = uid;
            this.author = author;
            this.title = title;
//            this.source = source;
//            this.destination = destination;
            this.dateOfJourney = dateOfJourney;
            this.timeOfJourney = timeOfJourney;
            this.body = body;
        }

        // [START post_to_map]
        @Exclude
        public Map<String, Object> toMap() {
            HashMap<String, Object> result = new HashMap<>();
            result.put("uid", uid);
            result.put("author", author);
            result.put("title", title);
//            result.put("source", source);
//            result.put("destination", destination);
            result.put("dateOfJourney", dateOfJourney);
            result.put("timeOfJourney", timeOfJourney);
            result.put("body", body);
//            result.put("starCount", starCount);
//            result.put("stars", stars);

            return result;
        }
        // [END post_to_map]
}

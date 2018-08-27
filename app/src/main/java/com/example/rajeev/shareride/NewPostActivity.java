package com.example.rajeev.shareride;

import android.app.DialogFragment;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rajeev.shareride.models.Post;
import com.example.rajeev.shareride.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class NewPostActivity extends BasicActivity {
    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    private EditText mTitleField;
    private EditText mBodyField;
    private EditText mPerson1;
    private EditText mPerson2;
    private EditText mPerson3;
    private EditText mPerson4;
    private EditText mPerson5;
    private EditText mContact1;
    private EditText mContact2;
    private EditText mContact3;
    private EditText mContact4;
    private EditText mContact5;
    private FloatingActionButton mSubmitButton;
    private Button mDate;
    private Button mTime;
    private String body;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTitleField = findViewById(R.id.source);
        mBodyField = findViewById(R.id.destination);
        mPerson1 = findViewById(R.id.person_1);
        mPerson2 = findViewById(R.id.person_2);
        mPerson3 = findViewById(R.id.person_3);
        mPerson4 = findViewById(R.id.person_4);
        mPerson5 = findViewById(R.id.person_5);
        mContact1 = findViewById(R.id.contact_1);
        mContact2 = findViewById(R.id.contact_2);
        mContact3 = findViewById(R.id.contact_3);
        mContact4 = findViewById(R.id.contact_4);
        mContact5 = findViewById(R.id.contact_5);
        mSubmitButton = findViewById(R.id.fab_submit_post);
        mDate = findViewById(R.id.date);
        mTime = findViewById(R.id.time);

        mDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "Date Picker");
            }
        });

        mTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getFragmentManager(), "Time Picker");
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        final String source = mTitleField.getText().toString();
        final String destination = mBodyField.getText().toString();
        final String dateOfJourney = "On: " + mDate.getText().toString();
        final String timeOfJourney = "At: " + mTime.getText().toString();
        final String title = source + " to " + destination;
        final String person1 = mPerson1.getText().toString();
        final String contact1 = mContact1.getText().toString();
        final String person2 = mPerson2.getText().toString();
        final String contact2 = mContact2.getText().toString();
        final String person3 = mPerson3.getText().toString();
        final String contact3 = mContact3.getText().toString();
        final String person4 = mPerson4.getText().toString();
        final String contact4 = mContact4.getText().toString();
        final String person5 = mPerson5.getText().toString();
        final String contact5 = mContact5.getText().toString();

        body = "";

        // Title is required
        if (TextUtils.isEmpty(source)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(destination)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        if(mDate.getText().toString().equalsIgnoreCase("date")){
            Toast.makeText(this, "Date Required", Toast.LENGTH_LONG).show();
            return;
        }

        if(mTime.getText().toString().equalsIgnoreCase("time")){
            Toast.makeText(this, "Time Required", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(person1)) {
            mPerson1.setError(REQUIRED);
            return;
        }

        if (TextUtils.isEmpty(contact1)) {
            mContact1.setError(REQUIRED);
            return;
        }

        body = body + person1 + "#" + contact1 + "@";


        if (!TextUtils.isEmpty(contact2)){
            if (TextUtils.isEmpty(person2)){
                mPerson2.setError(REQUIRED);
                return;
            }
        }

        body = body + person2 + "#" + contact2 + "@";

        if (!TextUtils.isEmpty(contact3)){
            if (TextUtils.isEmpty(person3)){
                mPerson3.setError(REQUIRED);
                return;
            }
        }

        body = body + person3 + "#" + contact3 + "@";

        if (!TextUtils.isEmpty(contact4)){
            if (TextUtils.isEmpty(person3)){
                mPerson4.setError(REQUIRED);
                return;
            }
        }

        body = body + person4 + "#" + contact4 + "@";

        if (!TextUtils.isEmpty(contact5)){
            if (TextUtils.isEmpty(person5)){
                mPerson5.setError(REQUIRED);
                return;
            }
        }

        body = body + person5 + "#" + contact5;


        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        if (user == null) {
                            Log.e(TAG, "User " + userId + " is unexpectedly null");
                            Toast.makeText(NewPostActivity.this,
                                    "Error: could not fetch user.",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            writeNewPost(userId, user.username, title, dateOfJourney, timeOfJourney, body);
                        }

                        setEditingEnabled(true);
                        finish();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                        setEditingEnabled(true);
                    }
                });
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }

    private void writeNewPost(String userId, String username, String title, String dateOfJourney, String timeOfJourney, String body) {
        String key = mDatabase.child("posts").push().getKey();
        Post post = new Post(userId, username, title, dateOfJourney, timeOfJourney, body);
        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/posts/" + key, postValues);
        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);
    }
}


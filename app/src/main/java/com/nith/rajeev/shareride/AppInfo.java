package com.nith.rajeev.shareride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AppInfo extends AppCompatActivity {


    private TextView firstContributor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
//
//        firstContributor = findViewById(R.id.first_contributor);
//        firstContributor.setMovementMethod(LinkMovementMethod.getInstance());
//        firstContributor.setTextColor(getResources().getColor(R.color.link_blue));
    }
}

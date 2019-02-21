package com.nith.rajeev.shareride;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.nith.rajeev.shareride.fragment.ActiveRides;
import com.nith.rajeev.shareride.fragment.PreviousRides;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FragmentPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            private final Fragment[] mFragments = new Fragment[]{
                    new ActiveRides(),
                    new PreviousRides(),
            };
            private final String[] mFragmentNames = new String[]{
                    "Active Rides",
                    "My Rides",
            };
            @Override
            public Fragment getItem(int position) {
                return mFragments[position];
            }
            @Override
            public int getCount() {
                return mFragments.length;
            }
            @Override
            public CharSequence getPageTitle(int position) {
                return mFragmentNames[position];
            }
        };
        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mPagerAdapter);
        getSupportActionBar().setElevation(0);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackgroundColor(Color.parseColor("#817BCB"));
        tabLayout.setTabTextColors(Color.parseColor("#B2AFF7"), Color.parseColor("#FFFFFF"));
        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FFFFFF"));
        tabLayout.setSelectedTabIndicatorHeight(8);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_new_post);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NewPostActivity.class));

            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == R.id.action_logout) {

            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return true;

        } else if(i == R.id.about){
            startActivity(new Intent(MainActivity.this, AppInfo.class));
            return true;

        }else if (i == R.id.rate_and_review){

            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
            Intent goToPlayStore = new Intent(Intent.ACTION_VIEW, uri);
            goToPlayStore.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY|
                                   Intent.FLAG_ACTIVITY_NEW_DOCUMENT|
                                   Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            try {
                startActivity(goToPlayStore);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName())));
            }

            return true;

        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class BloodFeedBase extends AppCompatActivity {
    BottomNavigationView mainBottomNav;
    FirebaseAuth mAuth;
    BloodFragment bloodFragment;
    private Spinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_feed_base);
        mainBottomNav = findViewById(R.id.mainBottomNav_blood);
        mAuth = FirebaseAuth.getInstance();

        mSpinner= (Spinner) findViewById(R.id.BloodGroupSelector);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.blood_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        bloodFragment = new BloodFragment();
        setupBottomNavigation();
        startFragment();
    }

    private void startFragment() {
        mSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String bloodgroup= mSpinner.getSelectedItem().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putString("blood", bloodgroup);
                bloodFragment.setArguments(bundle);
            }
        });

        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.main_container_blood,bloodFragment);
        trans.commit();
    }

    private void setupBottomNavigation() {
        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout_feed:
                        mAuth.signOut();
                        Intent intent = new Intent(BloodFeedBase.this, loginActivity.class);
                        startActivity(intent);
                    case R.id.user_profile:
                        Toast.makeText(BloodFeedBase.this,"HOME",Toast.LENGTH_SHORT).show();
                    case R.id.home:
                        Intent i = new Intent(BloodFeedBase.this,OrganizationProfile.class);
                        startActivity(i);
                        return true;
                }
                return true;
            }
        });

    }
}

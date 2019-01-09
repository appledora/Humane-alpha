package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VolunteerListFeedBase extends AppCompatActivity {
    private FloatingActionButton addBtn;
    private BottomNavigationView mainBottomNav;
    FirebaseAuth mAuth;
    String currentUserId,orgName = null;
    DatabaseReference ref;
    VolunteerFragment volunteerFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_list_feed_base);
        mainBottomNav = findViewById(R.id.mainBottomNav_vol);
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    orgName = dataSnapshot.child("Name").getValue().toString();

                    if(orgName != null){
                        volunteerFragment = new VolunteerFragment();
                        startFragment();}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        setupBottomNavigation();




        addBtn = findViewById(R.id.add_vol_btn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VolunteerListFeedBase.this, registerVolunteerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void startFragment() {

        Bundle bundle = new Bundle();
        bundle.putString("org", orgName);
        volunteerFragment.setArguments(bundle);
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.main_container_vol,volunteerFragment);
        trans.commit();
    }

    private void setupBottomNavigation() {
        mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout_feed:
                        mAuth.signOut();
                        Intent intent = new Intent(VolunteerListFeedBase.this, loginActivity.class);
                        startActivity(intent);
                    case R.id.user_profile:
                        Toast.makeText(VolunteerListFeedBase.this,"HOME",Toast.LENGTH_SHORT).show();
                    case R.id.home:
                        Intent i = new Intent(VolunteerListFeedBase.this,OrganizationProfile.class);
                        startActivity(i);
                        return true;
                }
                return true;
            }
        });
    }
}
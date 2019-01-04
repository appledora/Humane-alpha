package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class OrganizationProfile extends AppCompatActivity {

    private CardView pendingVerification;
    private CardView availableTasks;
    private CardView volunteerList;
    private TextView name ;

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference ref;
    String uid;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organization_ui_skull);

        pendingVerification = (CardView) findViewById(R.id.pending);
        availableTasks = (CardView)findViewById(R.id.available);
        volunteerList = (CardView)findViewById(R.id.volunteer);
        name = (TextView)findViewById(R.id.name_display);

        auth = FirebaseAuth.getInstance();
        uid = auth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    String nm = dataSnapshot.child("Name").getValue().toString();
                    name.setText(nm);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        pendingVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrganizationProfile.this,"PENDING",Toast.LENGTH_SHORT).show();

            }
        });


        availableTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrganizationProfile.this,"AVAILABLE",Toast.LENGTH_SHORT).show();
            }
        });

        volunteerList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OrganizationProfile.this,"Volunteer",Toast.LENGTH_SHORT).show();

            }
        });

        setupBottomNavigationView();

    }

    private void setupBottomNavigationView() {
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        auth.signOut();
                        Intent intent = new Intent(OrganizationProfile.this,loginActivity.class);
                        startActivity(intent);
                    case R.id.current_profile:
                        Toast.makeText(OrganizationProfile.this,"HOME",Toast.LENGTH_SHORT).show();
                    case R.id.feed:
                        Intent i = new Intent(OrganizationProfile.this,FeedBase.class);
                        startActivity(i);
                }
                return true;
            }
        });
    }
}

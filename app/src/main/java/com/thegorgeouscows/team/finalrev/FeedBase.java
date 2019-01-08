package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FeedBase extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference ref;
    private String tag, uid;

    private FloatingActionButton addPostBtn;
    private BottomNavigationView mainbottomNav, maintopNav;

    private FoodFragment foodFragment;
    private ClothFragment clothFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tag = dataSnapshot.child("ID").getValue(String.class);
                if (tag.equals("Organization")) {
                    setContentView(R.layout.activity_feed_base_org);

                    if (mAuth.getCurrentUser() != null) {

                        mainbottomNav = findViewById(R.id.mainBottomNav_org);
                        maintopNav = findViewById(R.id.topNav_org);
                        foodFragment = new FoodFragment();
                        clothFragment = new ClothFragment();


                        initializeFragment();

                        maintopNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container_org);
                                switch (item.getItemId()) {
                                    case R.id.foods:
                                        replaceFragment(foodFragment, currentFragment);
                                        return true;
                                    case R.id.clothe:
                                        replaceFragment(clothFragment, currentFragment);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                                switch (item.getItemId()) {
                                    case R.id.home:
                                        return true;
                                    case R.id.user_profile:
                                        Intent i = new Intent(FeedBase.this, DonatorProfile.class);
                                        startActivity(i);
                                        return true;
                                    case R.id.logout_feed:
                                        mAuth.signOut();
                                        Intent intent = new Intent(FeedBase.this, loginActivity.class);
                                        startActivity(intent);


                                    default:
                                        return false;
                                }
                            }
                        });


                    }
                } else if (tag.equals("Donator")) {
                    setContentView(R.layout.activity_feed_base);
                    if (mAuth.getCurrentUser() != null) {
                        mainbottomNav = findViewById(R.id.mainBottomNav);
                        maintopNav = findViewById(R.id.topNav);
                        foodFragment = new FoodFragment();
                        clothFragment = new ClothFragment();
                        initializeFragment();
                        maintopNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container_org);
                                switch (item.getItemId()) {
                                    case R.id.foods:
                                        replaceFragment(foodFragment, currentFragment);
                                        return true;
                                    case R.id.clothe:
                                        replaceFragment(clothFragment, currentFragment);
                                        return true;
                                    default:
                                        return false;
                                }
                            }
                        });
                        mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                                switch (item.getItemId()) {
                                    case R.id.home:


                                        return true;
                                    case R.id.user_profile:
                                        Intent i = new Intent(FeedBase.this, DonatorProfile.class);
                                        startActivity(i);
                                        return true;
                                    case R.id.logout_feed:
                                        mAuth.signOut();
                                        Intent intent = new Intent(FeedBase.this, loginActivity.class);
                                        startActivity(intent);


                                    default:
                                        return false;
                                }
                            }
                        });
                        addPostBtn = findViewById(R.id.add_post_btn);
                        addPostBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(FeedBase.this, FoodPostForm.class);
                                startActivity(intent);
                            }
                        });
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void initializeFragment() {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        if (tag.equals("Organization")) {
            trans.add(R.id.main_container_org, foodFragment);
            trans.add(R.id.main_container_org, clothFragment);
        } else if (tag.equals("Donator")) {
            trans.add(R.id.main_container, foodFragment);
            trans.add(R.id.main_container, clothFragment);
        }

        trans.hide(clothFragment);
        trans.commit();
    }

    public void replaceFragment(Fragment fragment, Fragment currentFragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (fragment == foodFragment) {
            transaction.hide(clothFragment);
        }

        if (fragment == clothFragment) {
            transaction.hide(foodFragment);
        }

        transaction.show(fragment);
        transaction.commit();
    }


}

package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FeedBase extends AppCompatActivity {
    private Toolbar mainToolbar;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private String current_user_id;

    private FloatingActionButton addPostBtn;

    private BottomNavigationView mainbottomNav,maintopNav;

    private FoodFragment foodFragment;
    private ClothFragment clothFragment;
    FragmentTransaction transaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_base);


        mAuth = FirebaseAuth.getInstance();


        if(mAuth.getCurrentUser() != null){

            mainbottomNav = findViewById(R.id.mainBottomNav);
            maintopNav = findViewById(R.id.topNav);
            foodFragment = new FoodFragment();
            clothFragment = new ClothFragment();


            initializeFragment();

            maintopNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                    Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_container);
                    switch (item.getItemId()){
                        case R.id.foods:
                            replaceFragment(foodFragment,currentFragment);
                            return true;
                        case R.id.clothe:
                            replaceFragment(clothFragment,currentFragment);
                            return true;
                        default:
                             return false;
                    }
                }
            });
            mainbottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                    switch(item.getItemId()){
                        case R.id.home:


                            return true;
                        case R.id.user_profile:
                            Intent i = new Intent(FeedBase.this,DonatorProfile.class);
                            startActivity(i);
                            return true;
                        case R.id.logout_feed:
                            mAuth.signOut();
                            Intent intent = new Intent(FeedBase.this,loginActivity.class);
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
                    Intent intent = new Intent(FeedBase.this,FoodPostForm.class);
                    startActivity(intent);
                }
            });
        }
    }

    public void initializeFragment(){
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        trans.add(R.id.main_container,foodFragment);
        trans.add(R.id.main_container,clothFragment);

        trans.hide(clothFragment);
        trans.commit();
    }

    public void replaceFragment(Fragment fragment,Fragment currentFragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(fragment == foodFragment){
            transaction.hide(clothFragment);
        }

        if (fragment == clothFragment){
            transaction.hide(foodFragment);
        }

        transaction.show(fragment);
        transaction.commit();
    }


}

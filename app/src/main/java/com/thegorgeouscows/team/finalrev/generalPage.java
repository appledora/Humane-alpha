 package com.thegorgeouscows.team.finalrev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

 public class generalPage extends AppCompatActivity {

    private Button mLogout;

     private FirebaseAuth mAuth;
     private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_page);

        ProgressDialog pd = new ProgressDialog(generalPage.this);
        pd.setMessage("Logging in");
        pd.show();

        mLogout= (Button) findViewById(R.id.logout);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()== null){

                    startActivity(new Intent(generalPage.this, loginActivity.class));
                }
            }
        };


        mLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });
    }
    protected void onStart() {
         super.onStart();

         mAuth.addAuthStateListener(mAuthListener);
    }
    private void logout(){
        mAuth.signOut();
    }
}

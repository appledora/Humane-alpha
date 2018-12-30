package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class
loginActivity extends AppCompatActivity {


    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mLoginButton;
    private Button mRegisterButton;

    private DatabaseReference ref;
    FirebaseAuth mAuth;
    String uid;
    public String tag,mag;
    private FirebaseAuth.AuthStateListener mAuthListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_card);



        mEmailField = (EditText) findViewById(R.id.entryEmailLog);
        mPasswordField = (EditText) findViewById(R.id.entryPasswordLog);

        mLoginButton = (Button) findViewById(R.id.login);
        mRegisterButton = (Button) findViewById(R.id.registerButton);

        mAuth = FirebaseAuth.getInstance();

      mAuthListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser()!= null){
                    startActivity(new Intent(loginActivity.this, generalPage.class));
                }
            }
        };

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startSignIn();

            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(loginActivity.this, LandingActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);
    }

    private void startSignIn(){

        String email= mEmailField.getText().toString();
        String password= mPasswordField.getText().toString();
        if(TextUtils.isEmpty(email)|| TextUtils.isEmpty(password)){
            Toast.makeText(loginActivity.this, "Empty Fields", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        uid = mAuth.getCurrentUser().getUid();
                        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                        ref.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()){
                                    tag = dataSnapshot.child("ID").getValue().toString();
                                    Log.i("my", tag);

                                    if(tag.equals("Organization")){
                                        Intent intent = new Intent(loginActivity.this, OrganizationProfile.class);
                                        startActivity(intent);
                                    }
                                    else if (tag.equals("Donator")) {
                                        Intent intent = new Intent(loginActivity.this, DonatorProfile.class);
                                        startActivity(intent);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    } else
                        Toast.makeText(loginActivity.this, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }
    }


}

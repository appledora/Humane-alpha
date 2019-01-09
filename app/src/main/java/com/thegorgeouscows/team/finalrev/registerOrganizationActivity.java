package com.thegorgeouscows.team.finalrev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class registerOrganizationActivity extends AppCompatActivity {
    private Button mTestButton;

    private EditText mNameEntry;
    private EditText mEmailEntry;
    private EditText mPasswordEntry;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressDialog mProgress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_organization);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mProgress = new ProgressDialog(this);

        mTestButton = (Button) findViewById(R.id.org_testButton);


        mNameEntry = (EditText) findViewById(R.id.org_entryName);
        mEmailEntry = (EditText) findViewById(R.id.org_entryEmail);
        mPasswordEntry = (EditText) findViewById(R.id.org_entryPassword);


        mTestButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                startRegister();

            }
        });
    }

    private void startRegister() {

        final String name = mNameEntry.getText().toString().trim();
        final String email = mEmailEntry.getText().toString().trim();
        String password = mPasswordEntry.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)) {

            mProgress.setMessage("Signing Up ");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(registerOrganizationActivity.this, "Successful!", Toast.LENGTH_LONG).show();
                        String userid = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentuserdata = mDatabase.child(userid);
                        currentuserdata.child("Name").setValue(name);
                        currentuserdata.child("Email").setValue(email);
                        currentuserdata.child("Image").setValue("https://firebasestorage.googleapis.com/v0/b/final-rev-app.appspot.com/o/post_images%2Fperson.png?alt=media&token=fafa0ebd-2114-4622-b391-79712b84ff7a");
                        currentuserdata.child("ID").setValue("Organization");
                        mProgress.dismiss();
                        Intent i = new Intent(registerOrganizationActivity.this, OrganizationProfile.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        } else
                            Toast.makeText(registerOrganizationActivity.this, "Error", Toast.LENGTH_LONG).show();
                    }
                    }
            );
        }

    }
}

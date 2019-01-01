package com.thegorgeouscows.team.finalrev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.UUID;

public class registerDonatorActivity extends AppCompatActivity {

    private Button mTestButton;

    private EditText mNameEntry;
    private EditText mEmailEntry;
    private EditText mPasswordEntry;
    private EditText mContact;



    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage;
    private StorageReference storageReference;

    private ProgressDialog mProgress;
    DatabaseReference currentuserdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donator);

        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        mProgress= new ProgressDialog(this);

        mTestButton= (Button) findViewById(R.id.testButton);


        mNameEntry= (EditText) findViewById(R.id.entryName);
        mEmailEntry= (EditText) findViewById(R.id.entryEmail);
        mPasswordEntry= (EditText) findViewById(R.id.entryPassword);
        mContact = (EditText) findViewById(R.id.reg_contact);


        mTestButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v){
                startRegister();

            }
        });
    }


    private void startRegister(){

        final String name= mNameEntry.getText().toString().trim();
        final String email= mEmailEntry.getText().toString().trim();
        String password= mPasswordEntry.getText().toString().trim();
        final String contact = mContact.getText().toString().trim();


        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email)){

            mProgress.setMessage("Signing Up ");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){

                        Toast.makeText(registerDonatorActivity.this,"Successful!", Toast.LENGTH_LONG).show();
                        String userid= mAuth.getCurrentUser().getUid();

                         currentuserdata = mDatabase.child(userid);
                        currentuserdata.child("Name").setValue(name);
                        currentuserdata.child("Email").setValue(email);
                        currentuserdata.child("ID").setValue("Donator");
                        currentuserdata.child("Image").setValue("Default");
                        currentuserdata.child("Contact").setValue(contact);

                        mProgress.dismiss();

                        Intent i= new Intent(registerDonatorActivity.this, DonatorProfile.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                    }
                    else
                        Toast.makeText(registerDonatorActivity.this,"Error", Toast.LENGTH_LONG).show();
                }
                }
            );
        }

    }



}

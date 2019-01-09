package com.thegorgeouscows.team.finalrev;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

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
    private FirebaseFirestore firebaseFirestore;

    private Spinner mSpinner;

    private ProgressDialog mProgress;
    DatabaseReference currentuserdata;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_donator);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        mProgress = new ProgressDialog(this);

        mTestButton = (Button) findViewById(R.id.testButton);

        mSpinner= (Spinner) findViewById(R.id.BloodGroupSelector);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this, R.array.blood_groups, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);

        mNameEntry = (EditText) findViewById(R.id.entryName);
        mEmailEntry = (EditText) findViewById(R.id.entryEmail);
        mPasswordEntry = (EditText) findViewById(R.id.entryPassword);
        mContact = (EditText) findViewById(R.id.reg_contact);

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
        final String contact = mContact.getText().toString().trim();
        final String bloodgroup= mSpinner.getSelectedItem().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(bloodgroup)) {

            mProgress.setMessage("Signing Up ");
            mProgress.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                                                                            @Override
                                                                                            public void onComplete(@NonNull Task<AuthResult> task) {
                                                                                                if (task.isSuccessful()) {

                                                                                                    Toast.makeText(registerDonatorActivity.this, "Successful!", Toast.LENGTH_LONG).show();
                                                                                                    String userid = mAuth.getCurrentUser().getUid();

                                                                                                    currentuserdata = mDatabase.child(userid);
                                                                                                    currentuserdata.child("Name").setValue(name);
                                                                                                    currentuserdata.child("Email").setValue(email);
                                                                                                    currentuserdata.child("ID").setValue("Donator");
                                                                                                    currentuserdata.child("Image").setValue("https://firebasestorage.googleapis.com/v0/b/final-rev-app.appspot.com/o/post_images%2Fperson.png?alt=media&token=fafa0ebd-2114-4622-b391-79712b84ff7a");
                                                                                                    currentuserdata.child("Contact").setValue(contact);
                                                                                                    currentuserdata.child("Bloodgroups").setValue(bloodgroup);
                                                                                                    mProgress.dismiss();

                                                                                                    Intent i = new Intent(registerDonatorActivity.this, DonatorProfile.class);
                                                                                                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                                    startActivity(i);
                                                                                                } else
                                                                                                    Toast.makeText(registerDonatorActivity.this, "Error", Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        }
            );


            Map<String,Object> BloodList = new HashMap<>();
            BloodList.put("buserName",name);
            BloodList.put("bbloodGroup",bloodgroup);
            BloodList.put("bcontact",contact);

            firebaseFirestore.collection("BLOODLIST").add(BloodList).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                @Override
                public void onComplete(@NonNull Task<DocumentReference> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(registerDonatorActivity.this,"POST WAS ADDED",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

    }


}
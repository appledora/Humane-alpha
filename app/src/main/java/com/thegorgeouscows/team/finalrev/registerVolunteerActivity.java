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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registerVolunteerActivity extends AppCompatActivity {
    private EditText volName,volContact,volAddress,volEmail;
    EditText vusername,vaddress,vcontact,vemail;
    private Button register;
    DatabaseReference ref;
    String currentUserID;
    String orgName;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_volunteer);
        vusername = findViewById(R.id.entryName_vol);
        vaddress = findViewById(R.id.entryAddress_vol);
        vemail = findViewById(R.id.entryEmail_vol);
        vcontact = findViewById(R.id.reg_contact_vol);
        register = findViewById(R.id.reg_volunteer);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
                Toast.makeText(registerVolunteerActivity.this,"REGISTERED",Toast.LENGTH_LONG);
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        currentUserID = firebaseAuth.getCurrentUser().getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    orgName = dataSnapshot.child("Name").getValue().toString();
                    Log.i("my ORGNAME: ",orgName);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void startRegister() {
        String name = vusername.getText().toString().trim();
        String email = vemail.getText().toString().trim();
        String address = vaddress.getText().toString().trim();
        String  contact = vcontact.getText().toString().trim();

        if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(address) && !TextUtils.isEmpty(contact)){

            Map<String,Object> volCred = new HashMap<>();
            volCred.put("vusername",name);
            volCred.put("vemail",email);
            volCred.put("vaddress",address);
            volCred.put("vcontact",contact);
            volCred.put("ID","Volunteer");
            volCred.put("Organization",orgName);

        Log.i("my: ","went past MAPPING");
        FirebaseFirestore rootref = FirebaseFirestore.getInstance();
        CollectionReference colref = rootref.collection("OrgList").document(orgName).collection("Volunteers");
        Log.i("my: ","crossed referencing");
        colref.add(volCred);
        Log.i("my: ","ADDED to STORE");
            Intent intent = new Intent(registerVolunteerActivity.this,OrganizationProfile.class);
            startActivity(intent);
        }
    }
}

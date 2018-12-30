package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LandingActivity extends AppCompatActivity {

    private Button mDonatorReg;
    private Button mVolunteerReg;
    private Button mOrganizationReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mDonatorReg= (Button) findViewById(R.id.donatorReg);
        mOrganizationReg= (Button) findViewById(R.id.organizationReg);
        mVolunteerReg= (Button) findViewById(R.id.volunteerReg);

        mDonatorReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(LandingActivity.this, registerDonatorActivity.class);
                startActivity(i);
            }
        });
        mOrganizationReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(LandingActivity.this, registerVolunteerActivity.class);
                startActivity(i);
            }
        });
        mOrganizationReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(LandingActivity.this, registerOrganizationActivity.class);
                startActivity(i);
            }
        });

    }
}

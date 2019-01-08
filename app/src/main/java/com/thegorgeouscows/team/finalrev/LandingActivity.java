package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class LandingActivity extends AppCompatActivity {

    private ImageButton mDonatorReg;
    private ImageButton mOrganizationReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        mDonatorReg= (ImageButton) findViewById(R.id.donatorReg);
        mOrganizationReg= (ImageButton) findViewById(R.id.organizationReg);


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
                Intent i= new Intent(LandingActivity.this, registerOrganizationActivity.class);
                startActivity(i);
            }
        });

    }
}

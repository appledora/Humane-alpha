package com.thegorgeouscows.team.finalrev;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class resetPasswordActivity extends AppCompatActivity {
    private EditText mEmailText;
    private Button  mResetPassword;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        mEmailText= (EditText) findViewById(R.id.resetEmail);
        mResetPassword= (Button) findViewById(R.id.resetButton);

        mAuth= FirebaseAuth.getInstance();

        mResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= mEmailText.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter your Email", Toast.LENGTH_SHORT).show();
                    return;

                }

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(resetPasswordActivity.this,"Check Email to Reset your password",Toast.LENGTH_SHORT).show();
                                    Intent i=new Intent(resetPasswordActivity.this, loginActivity.class);
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(resetPasswordActivity.this,"Failed to Send Email",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

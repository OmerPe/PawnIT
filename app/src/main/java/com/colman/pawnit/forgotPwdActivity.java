package com.colman.pawnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class forgotPwdActivity extends AppCompatActivity{

    private ProgressBar progressBar;
    private EditText etEmail;
    private Button button;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

        progressBar = (ProgressBar) findViewById(R.id.forgotPwd_pbar);
        etEmail = (EditText) findViewById(R.id.forgotPwd_email);
        button = (Button) findViewById(R.id.forgotPwd_btn);

        mAuth = FirebaseAuth.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword() {
        String email = etEmail.getText().toString().trim();

        if(email.isEmpty()){
            etEmail.setError("Email is Required");
            etEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Invalid Email");
            etEmail.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(forgotPwdActivity.this,"if this email exists, please check your email",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(forgotPwdActivity.this, loginActivity.class));
                }else{
                    Toast.makeText(forgotPwdActivity.this,"something went wrong, try again",Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
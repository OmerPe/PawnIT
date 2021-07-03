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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPwd;
    private ProgressBar progressBar;
    private Button loginBtn;
    private EditText etEmail, etPwd;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        register = (TextView) findViewById(R.id.login_register);
        register.setOnClickListener(this);
        forgotPwd = (TextView) findViewById(R.id.login_frgtpwd);
        forgotPwd.setOnClickListener(this);
        loginBtn = (Button) findViewById(R.id.login_btn);
        loginBtn.setOnClickListener(this);

        etEmail = (EditText) findViewById(R.id.login_email);
        etPwd = (EditText) findViewById(R.id.login_password);

        progressBar = (ProgressBar) findViewById(R.id.login_pbar);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_register:
                finish();
                startActivity(new Intent(this, registerActivity.class));
                break;
            case R.id.login_frgtpwd:
                startActivity(new Intent(this, forgotPwdActivity.class));
                break;
            case R.id.login_btn:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPwd.getText().toString().trim();

        if(email.isEmpty()){
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            etEmail.setError("Please enter email correctly");
            etEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            etPwd.setError("Password is required");
            etPwd.requestFocus();
            return;
        }
        if(password.length() < 6){
            etPwd.setError("Password is too short");
            etPwd.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser fbuser = FirebaseAuth.getInstance().getCurrentUser();

                    if(fbuser.isEmailVerified()){
                        startActivity(new Intent(loginActivity.this,MainActivity.class));

                    }else{
                        fbuser.sendEmailVerification();
                        Toast.makeText(loginActivity.this,"Please check your email to verify your account",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(loginActivity.this,"Failed to log in, check credentials", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}
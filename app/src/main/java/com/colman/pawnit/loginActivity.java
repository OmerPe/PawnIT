package com.colman.pawnit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class loginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, forgotPwd;
    private ProgressBar progressBar;
    private Button loginBtn;

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

        progressBar = (ProgressBar) findViewById(R.id.login_pbar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_register:
                startActivity(new Intent(this, registerActivity.class));
                break;
            case R.id.login_frgtpwd:
                break;
        }
    }
}
package com.colman.pawnitv2.Ui.User;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.colman.pawnitv2.R;

public class LoginFragment extends Fragment {

    private ProgressBar progressBar;
    private EditText etEmail, etPwd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        progressBar = (ProgressBar)view.findViewById(R.id.login_pbar);
        progressBar.setVisibility(View.GONE);
        etEmail = view.findViewById(R.id.login_email);
        etPwd = view.findViewById(R.id.login_password);

        view.findViewById(R.id.login_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment);
            }
        });
        view.findViewById(R.id.login_frgtpwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_forgotPasswordFragment);
            }
        });

        view.findViewById(R.id.login_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });



        return view;
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

        //TODO : Login logic

    }
}
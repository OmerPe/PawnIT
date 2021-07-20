package com.colman.pawnitv2.Ui.User;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;

import com.colman.pawnitv2.R;

public class ForgotPasswordFragment extends Fragment {
    private ProgressBar progressBar;
    private EditText etEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.forgotPwd_pbar);
        etEmail = (EditText) view.findViewById(R.id.forgotPwd_email);
        view.findViewById(R.id.forgotPwd_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        return view;
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

        //TODO : forgot password logic
    }
}
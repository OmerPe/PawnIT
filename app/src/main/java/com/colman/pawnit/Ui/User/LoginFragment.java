package com.colman.pawnit.Ui.User;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.colman.pawnit.Model.Model;
import com.colman.pawnit.R;

public class LoginFragment extends Fragment {

    private ProgressBar progressBar;
    private EditText etEmail, etPwd;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        progressBar = (ProgressBar) view.findViewById(R.id.login_pbar);
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
                userLogin(v);
            }
        });


        return view;
    }

    private void userLogin(View v) {

        String email = etEmail.getText().toString().trim();
        String password = etPwd.getText().toString().trim();

        if (email.isEmpty()) {
            etEmail.setError("Email is required!");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter email correctly");
            etEmail.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            etPwd.setError("Password is required");
            etPwd.requestFocus();
            return;
        }
        if (password.length() < 6) {
            etPwd.setError("Password is too short");
            etPwd.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        Model.instance.logIn(email, password, task -> {
            if (task.isSuccessful()) {
                if (Model.instance.getLoggedUser().isEmailVerified()) {
                    Toast.makeText(getActivity(), "Logged in successfully!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(v).navigateUp();
                } else {
                    Model.instance.getLoggedUser().sendEmailVerification();
                    Toast.makeText(getActivity(), "Email needs verification, please check your email.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Error logging in, make sure Credentials are ok.", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        });

    }
}
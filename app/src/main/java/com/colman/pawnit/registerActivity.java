package com.colman.pawnit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.colman.pawnit.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class registerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etFullName, etEmail, etPwd;
    private ProgressBar progressBar;
    private Button registerUser, dateButton;
    private DatePickerDialog datePickerDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        initDatePicker();
        dateButton = (Button) findViewById(R.id.register_datebtn);
        dateButton.setText(getTodaysDate());

        etFullName = (EditText) findViewById(R.id.register_name);

        etEmail = (EditText) findViewById(R.id.register_email);
        etPwd = (EditText) findViewById(R.id.register_pwd);

        registerUser = (Button) findViewById(R.id.register_btn);
        registerUser.setOnClickListener(this);

        progressBar = (ProgressBar) findViewById(R.id.register_pbar);
    }

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private String makeDateString(int day, int month, int year) {

        String m = "JAN";
        switch (month) {
            case 1:
                m = "JAN";
                break;
            case 2:
                m = "FEB";
                break;
            case 3:
                m = "MAR";
                break;
            case 4:
                m = "APR";
                break;
            case 5:
                m = "MAY";
                break;
            case 6:
                m = "JUN";
                break;
            case 7:
                m = "JUL";
                break;
            case 8:
                m = "AUG";
                break;
            case 9:
                m = "SEP";
                break;
            case 10:
                m = "OCT";
                break;
            case 11:
                m = "NOV";
                break;
            case 12:
                m = "DEC";
                break;
        }

        return m + " " + day + " " + year;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_btn:
                RegisterUser();
                break;
        }

    }

    private void RegisterUser() {
        String email = etEmail.getText().toString().trim();
        String pwd = etPwd.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();

        String date = dateButton.getText().toString().trim();

        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required!!");
            etFullName.requestFocus();
            return;
        }

        if (date.equals(getTodaysDate())) {
            dateButton.setError("Date is required!!");
            dateButton.requestFocus();
            return;
        }
        final Date dateOfBirth = getDate(dateButton.getText().toString().trim());
        if (dateOfBirth == null) {
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email is required!!");
            etEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Email entered is not valid!");
            etEmail.requestFocus();
            return;
        }
        if (pwd.isEmpty()) {
            etPwd.setError("Password is required!!");
            etPwd.requestFocus();
            return;
        }
        if (pwd.length() < 6) {
            etPwd.setError("Password is to short!!");
            etPwd.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(fullName, dateOfBirth, email);

                    FirebaseDatabase.getInstance().getReference("Users").
                            child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(registerActivity.this,"User has been registered successfully!", Toast.LENGTH_LONG).show();

                                //redirect to where i want to go
                            }else{
                                Toast.makeText(registerActivity.this,"OOPS something went wrong. user is not registered, try again!", Toast.LENGTH_LONG).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }else{
                    Toast.makeText(registerActivity.this,"OOPS something went wrong. user is not registered, try again!", Toast.LENGTH_LONG).show();
                    task.getException().printStackTrace();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }

    private Date getDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String[] dateParsed = date.split(" ");
        String month = getMonth(dateParsed[0]);
        String finalDate = dateParsed[1] + "/" + month + "/" + dateParsed[2];
        Date dateOfBirth = null;
        try {
            dateOfBirth = sdf.parse(finalDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateOfBirth != null) {

        }

        return dateOfBirth;
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    private String getMonth(String month) {
        if (month.equals("JAN")) {
            return "01";
        }
        if (month.equals("FEB")) {
            return "02";
        }
        if (month.equals("MAR")) {
            return "03";
        }
        if (month.equals("APR")) {
            return "04";
        }
        if (month.equals("MAY")) {
            return "05";
        }
        if (month.equals("JUN")) {
            return "06";
        }
        if (month.equals("JUL")) {
            return "07";
        }
        if (month.equals("AUG")) {
            return "08";
        }
        if (month.equals("SEP")) {
            return "09";
        }
        if (month.equals("OCT")) {
            return "10";
        }
        if (month.equals("NOV")) {
            return "11";
        }
        if (month.equals("DEC")) {
            return "12";
        }
        return "01";

    }
}
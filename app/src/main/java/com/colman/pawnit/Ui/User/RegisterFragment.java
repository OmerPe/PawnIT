package com.colman.pawnit.Ui.User;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.colman.pawnit.Model.Model;
import com.colman.pawnit.Model.User;
import com.colman.pawnit.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RegisterFragment extends Fragment {

    private EditText etFullName, etEmail, etPwd;
    private ProgressBar progressBar;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);

        initDatePicker();

        dateButton = (Button) view.findViewById(R.id.register_datebtn);
        dateButton.setText(getTodaysDate());
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDatePicker(v);
            }
        });

        etFullName = (EditText) view.findViewById(R.id.register_name);
        etEmail = (EditText) view.findViewById(R.id.register_email);
        etPwd = (EditText) view.findViewById(R.id.register_pwd);

        view.findViewById(R.id.register_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterUser(v);
            }
        });


        progressBar = (ProgressBar) view.findViewById(R.id.register_pbar);

        return view;
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

        datePickerDialog = new DatePickerDialog(getContext(), style, dateSetListener, year, month, day);
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

    private void RegisterUser(View v) {
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
        final Date dateOfBirth = getDate(date);
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

        Model.instance.signUpUser(email, pwd, (task -> {
            if (task.isSuccessful()) {
                User user = new User();
                user.setUid(Model.instance.getLoggedUser().getUid());
                user.setEmail(email);
                user.setDateOfBirth(getDate(date));
                user.setUserName(fullName);

                Model.instance.createUser(user,()->{
                    Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_homeFragment);
                });
            } else {
                Toast.makeText(getActivity(), "Failed to signup make sure everything is ok", Toast.LENGTH_SHORT).show();
            }
            progressBar.setVisibility(View.GONE);
        }));
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

    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

}
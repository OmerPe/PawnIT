package com.colman.pawnit.Ui;

import androidx.appcompat.app.AppCompatDialogFragment;

import android.app.AlertDialog;
import android.os.Bundle;
import android.app.Dialog;

import com.colman.pawnit.Model.Model;

public class ContactPopup extends AppCompatDialogFragment {
    String email;

    public ContactPopup(String email) {
        this.email = email;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("for more information, contact:")
                .setMessage(email)
                .setPositiveButton("Close", (dialogInterface, i) -> {

                });
        return builder.create();
    }
}

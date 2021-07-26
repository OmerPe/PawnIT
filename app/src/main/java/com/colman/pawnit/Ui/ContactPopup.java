package com.colman.pawnit.Ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

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

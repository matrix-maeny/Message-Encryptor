package com.matrix_maeny.messageencryptor.alertDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;

import com.matrix_maeny.messageencryptor.DataCenter;
import com.matrix_maeny.messageencryptor.R;

import java.util.Objects;

public class PasswordAskingDialog extends AppCompatDialogFragment {

    private PasswordAskingDialogListener listener;

    private EditText userPassword;
     AppCompatButton okBtn;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.password_asking_layout, null);
        builder.setView(root);


        userPassword = root.findViewById(R.id.userPassword);
        okBtn = root.findViewById(R.id.okBtnUser2);


        okBtn.setOnClickListener(v -> {
            if (checkUserPassword()) {
                listener.evaluateUserPassword();
                dismiss();
            }
        });


        return builder.create();
    }

    private boolean checkUserPassword() {

        String password = "";
        try {
            password = userPassword.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.equals("")) {
            Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!DataCenter.password.equals(password)) {
            Toast.makeText(getContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (PasswordAskingDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface PasswordAskingDialogListener {
        void evaluateUserPassword();
    }
}

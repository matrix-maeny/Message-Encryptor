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
import androidx.fragment.app.DialogFragment;

import com.matrix_maeny.messageencryptor.DataCenter;
import com.matrix_maeny.messageencryptor.R;

import java.util.Objects;

public class PasswordDialog extends AppCompatDialogFragment {

    private PasswordDialogListener listener;

    private EditText passwordTest, passwordConform;
    private AppCompatButton okBtn;

    private String password = null;
    private String password2 = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.password_dialog, null);
        builder.setView(root);


        passwordConform = root.findViewById(R.id.passwordConform);
        passwordTest = root.findViewById(R.id.passwordTest);
        okBtn = root.findViewById(R.id.okBtnPass);


        okBtn.setOnClickListener(v -> {
            if (checkPassword()) {
                listener.getPassword(password);
                dismiss();
            }
        });


        return builder.create();
    }

    private boolean checkPassword() {

        try {
            password = passwordTest.getText().toString();
            password2 = passwordConform.getText().toString();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.equals("")) {
            Toast.makeText(getContext(), "Please enter password", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(password2)) {
            Toast.makeText(getContext(), "Password didn't match", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.length() < 5) {
            Toast.makeText(getContext(), "Password should contain at-least 4 characters", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (PasswordDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface PasswordDialogListener {
        void getPassword(String password);
    }
}

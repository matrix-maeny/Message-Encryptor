package com.matrix_maeny.messageencryptor.alertDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.matrix_maeny.messageencryptor.R;


public class ChoiceAskDialog extends AppCompatDialogFragment {

    private ChoiceAskDialogListener listener = null;
    private RadioButton radioButtonYes, radioButtonNo;
    AppCompatButton okBtn;

    private int choice = 0;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        ContextThemeWrapper wrapper = new ContextThemeWrapper(getContext(), androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View root = inflater.inflate(R.layout.choice_ask_dialog, null);
        builder.setView(root);

        radioButtonNo = root.findViewById(R.id.radioButtonNo);
        radioButtonYes = root.findViewById(R.id.radioButtonYes);
        okBtn = root.findViewById(R.id.okBtn);

        radioButtonNo.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {
                radioButtonYes.setChecked(false);
                choice = 2;
            }
        });

        radioButtonYes.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                radioButtonNo.setChecked(false);
                choice = 1;
            }
        });

        okBtn.setOnClickListener(v -> {
            if (choice == 0) {
                Toast.makeText(getContext(), "please select any one of choice", Toast.LENGTH_SHORT).show();
            } else {
                listener.getChoiceAsk(choice);
                dismiss();
            }
        });


        return builder.create();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (ChoiceAskDialogListener) context;
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Some error occurred:1", Toast.LENGTH_SHORT).show();
        }
    }

    public interface ChoiceAskDialogListener {
        void getChoiceAsk(int choice);
    }
}

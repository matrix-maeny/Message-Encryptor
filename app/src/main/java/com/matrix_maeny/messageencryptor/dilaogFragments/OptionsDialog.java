package com.matrix_maeny.messageencryptor.dilaogFragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.matrix_maeny.messageencryptor.R;


public class OptionsDialog extends BottomSheetDialogFragment {


    private BottomSheetListener listener = null;
    TextView encryptOpt, decryptOpt;

    public OptionsDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_options_dialog, container, false);

        encryptOpt = view.findViewById(R.id.encryptOpt);
        decryptOpt = view.findViewById(R.id.decryptOpt);

        encryptOpt.setOnClickListener(encryptOptListener);
        decryptOpt.setOnClickListener(decryptOptListener);

        return view;
    }


    View.OnClickListener encryptOptListener = v -> {
        listener.getEncryptionChoice(0);
        dismiss();
    };
    View.OnClickListener decryptOptListener = v -> {
        listener.getEncryptionChoice(1);
        dismiss();
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            listener = (BottomSheetListener) context;
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public interface BottomSheetListener {
        void getEncryptionChoice(int choice);
    }
}
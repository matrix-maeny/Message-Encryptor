package com.matrix_maeny.messageencryptor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix_maeny.messageencryptor.alertDialogs.PasswordAskingDialog;
import com.matrix_maeny.messageencryptor.encryptclasses.Decryption;
import com.matrix_maeny.messageencryptor.encryptclasses.Encryption;

import java.util.Objects;

public class DisplayActivity extends AppCompatActivity implements PasswordAskingDialog.PasswordAskingDialogListener {

    private TextView msgTxtOut;
    private ProgressBar progressBar;

    private String message = null;
    private boolean isPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        if (DataCenter.state == 0) {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Encrypted Message");
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle("Decrypted Message");
        }

        msgTxtOut = findViewById(R.id.msgTxtOut);
        progressBar = findViewById(R.id.progressBar);

        startWork();


    }

    private void startWork() {
        if (DataCenter.state == 0) {
            startEncryption();
        } else {
            startDecryption();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.display_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        shareTxt();

        return super.onOptionsItemSelected(item);
    }

    private void shareTxt() {
        if (message != null) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(intent);
        } else {
            Toast.makeText(this, "wait...", Toast.LENGTH_SHORT).show();
        }
    }


    private void startEncryption() {
        this.message = Encryption.encryptMessage(DataCenter.rawMessage);
        showData();


    }

    private void startDecryption() {

        if (Decryption.decryptMessage(DataCenter.encryptedMessage)) {
            message = Decryption.decryptedMessage;
            isPassword = message.contains("password=");

            if (isPassword) {
                //            showData();
                // show dialog to enter password... create it first....
                DataCenter.password = getPassword();

                PasswordAskingDialog dialog = new PasswordAskingDialog();
                dialog.show(getSupportFragmentManager(), "password dialog");
            } else {
                showData();
            }
        }
    }

    private void showSecurityDialog() {

    }

    @NonNull
    private String getPassword() {

        int tempIdx = message.indexOf("password=") + 9;

        return message.substring(tempIdx);

    }

    private void showData() {
        if (isPassword) {
            int index = message.indexOf("password=");
            message = message.substring(0, index);
        }
        msgTxtOut.setText(this.message);
        progressBar.setVisibility(View.GONE);
        DataCenter.isPassword = false;
    }

    @Override
    public void evaluateUserPassword() {
        showData();
    }


}
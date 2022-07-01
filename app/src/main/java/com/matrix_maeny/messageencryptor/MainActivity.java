package com.matrix_maeny.messageencryptor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.matrix_maeny.messageencryptor.alertDialogs.ChoiceAskDialog;
import com.matrix_maeny.messageencryptor.alertDialogs.PasswordDialog;
import com.matrix_maeny.messageencryptor.dilaogFragments.OptionsDialog;

public class MainActivity extends AppCompatActivity implements OptionsDialog.BottomSheetListener, ChoiceAskDialog.ChoiceAskDialogListener, PasswordDialog.PasswordDialogListener {


    private String message = null;

    private EditText msgTextIn;
    private boolean isPassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msgTextIn = findViewById(R.id.msgTextIn);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.about_app:
                // goto about activity
                startActivity(new Intent(MainActivity.this,AboutActivity.class));
                break;
            case R.id.done_texting:
                // encrypt or decrypt message;
                showMessageDialog();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMessageDialog() {
        if (checkMsg()) {
            OptionsDialog dialog = new OptionsDialog();
            dialog.show(getSupportFragmentManager(), dialog.getTag());
        }
    }

    private boolean checkMsg() {

        try {
            message = msgTextIn.getText().toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Please enter message", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (message.equals("")) {
            Toast.makeText(this, "Please enter message", Toast.LENGTH_SHORT).show();

            return false;
        }


        return true;
    }


    @Override
    public void getEncryptionChoice(int choice) {
        DataCenter.state = choice;
        switch (choice) {
            case 0:
                // encrypt, open a dialog to ask if user wants to set password to the data;
                showChoiceAskDialog();
                break;
            case 1:
                // decrypt; open a dialog to enter the password, if exists;
                startDecryption();
                break;
        }



    }

    private void showChoiceAskDialog() {
        ChoiceAskDialog dialog = new ChoiceAskDialog();
        dialog.show(getSupportFragmentManager(), "choice dialog");
    }

    @Override
    public void getChoiceAsk(int choice) {
        if (choice == 1) {
            // show password creation
            showPasswordDialog();
        } else if (choice == 2) {
            // go to direct encryption
            startEncryption();
        }

    }

    private void showPasswordDialog() {
        PasswordDialog dialog = new PasswordDialog();
        dialog.show(getSupportFragmentManager(), "password dialog");
    }

    @Override
    public void getPassword(String password) {
        if (password != null) {

            String temp = "password=" + password;
            message = message.concat(temp);

            DataCenter.isPassword = true;
            startEncryption();

        }
    }

    private void startEncryption() {
        DataCenter.rawMessage = message;

        startActivity(new Intent(MainActivity.this, DisplayActivity.class));
    }

    private void startDecryption(){

        DataCenter.encryptedMessage = message;
        startActivity(new Intent(MainActivity.this, DisplayActivity.class));
    }



}
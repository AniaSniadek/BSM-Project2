package com.example.fingerprint;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

public class NewPassword extends AppCompatActivity {

    EditText textNewPassword;
    Button saveButton;
    String password;
    String encryptedPassword;
    Encryption encryptionClass = new Encryption();
    EditText key;
    private static final String FILE_NAME = "key.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);

        textNewPassword = findViewById(R.id.textNewPassword);
        saveButton = findViewById(R.id.buttonSavePassword);

        key = (EditText) findViewById(R.id.key);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password = textNewPassword.getText().toString().trim();
                String klucz = key.getText().toString();
                try {
                    encryptedPassword = encryptionClass.encrypt(password, klucz);
                    writeNewPasswordToFile(encryptedPassword);
                    saveKey(klucz);
                    logOut();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void saveNewPassword(String password)
    {
        try
        {
            File path = Environment.getExternalStorageDirectory();
            File file = new File(path, "password.txt");
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(password);
            bw.close();

            Toast.makeText(this, "Zapisano nowe hasło", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this, "Wystąpił błąd podczas zapisywania hasła", Toast.LENGTH_SHORT).show();
        }
    }

    private void writeNewPasswordToFile(String password)
    {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
            {
                String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                requestPermissions(permissions,1);
            }
            else {
                saveNewPassword(password); }
        }
        else { saveNewPassword(password); }

    }

    private void saveKey(String text) {
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void logOut() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

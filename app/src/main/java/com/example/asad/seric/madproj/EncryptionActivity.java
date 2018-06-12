package com.example.asad.seric.madproj;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class EncryptionActivity extends AppCompatActivity {

    private static final String TAG = "EncryptionActivity";
    EditText in, pass, out;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.encryption_activity);

        in = findViewById(R.id.In);
        pass = findViewById(R.id.Pass);
        out = findViewById(R.id.Out);
        go = findViewById(R.id.GO);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = in.getText().toString();
                String password = pass.getText().toString();
                try {
                    out.setText(
                            AESCrypt.encrypt(password, input)
                    );
                } catch (GeneralSecurityException e) {
                    Log.d(TAG, "onClick: Encryption broken");
                }
            }
        });

    }
}

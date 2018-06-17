package com.example.asad.seric.madproj;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class EncryptionActivity extends AppCompatActivity {

    private static final String TAG = "EncryptionActivity";
    EditText in, pass;
    TextView out;
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
                    out.setTextSize(10);
                    out.setText(
                            AESCrypt.encrypt(password, input)
                    );
                } catch (GeneralSecurityException e) {
                    Log.d(TAG, "onClick: Encryption broken");
                }

                Log.d(TAG, "onClick: " + input);
            }
        });

        out.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                   ClipData clip = ClipData.newPlainText("Encrypted Text",
                           out.getText().toString());
                   clipboard.setPrimaryClip(clip);
                   Toast.makeText(
                           getApplicationContext(),
                           "Text copied to clipboard",
                           Toast.LENGTH_SHORT
                   ).show();
               }
           }
        );

    }
}

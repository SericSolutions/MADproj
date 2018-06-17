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

public class DecryptionActivity extends AppCompatActivity {

    private static final String TAG = "DecryptionActivity";
    EditText pass;
    TextView out, in;
    Button go;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.decryption_activity);


        in = findViewById(R.id.In);
        pass = findViewById(R.id.Pass);
        out = findViewById(R.id.Out);
        go = findViewById(R.id.GO);

        Paste();


        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = in.getText().toString();
                String password = pass.getText().toString();
                try {
                    out.setText(
                            AESCrypt.decrypt(password, input)
                    );
                } catch (GeneralSecurityException e) {
                    Log.d(TAG, "onClick: Encryption broken");
//                    Toast.makeText(
//                            getApplicationContext(),
//                            "Incorrect Password",
//                            Toast.LENGTH_LONG
//                    ).show();
                    pass.setError(
                            "Wrong password"
                    );
                }
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

        in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Paste();
            }
        });
    }

    private void Paste(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

        if(clipboard.hasPrimaryClip()){
            in.setTextSize(10);
            in.setText(
                    clipboard
                            .getPrimaryClip()
                            .getItemAt(0)
                            .getText()
                            .toString()
            );

            Toast.makeText(
                    this,
                    "Got text from clipboard",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }
}

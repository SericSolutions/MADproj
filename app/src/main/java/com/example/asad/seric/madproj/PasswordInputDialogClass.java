package com.example.asad.seric.madproj;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import com.scottyab.aescrypt.AESCrypt;
import java.security.GeneralSecurityException;
import static android.content.ContentValues.TAG;


public class PasswordInputDialogClass extends Dialog implements
        android.view.View.OnClickListener {
    
    String KEY;
    
    Activity c;
    Dialog d;
    Button yes;
    EditText SN, UN, pass;

    public PasswordInputDialogClass(Activity a, String key) {
        super(a);
        KEY = key;
        this.c = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.passwordinput_dialog);
        yes = (Button) findViewById(R.id.btn_yes);
        SN = findViewById(R.id.SN);
        UN = findViewById(R.id.UN);
        pass = findViewById(R.id.Pass);
        yes.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                save();
                break;
            default:
                break;
        }
        dismiss();
    }

    private void save() {
        SQLiteHelper s = new SQLiteHelper(getContext());
        try {
            s.addPassword(0,
                SN.getText().toString(),
                UN.getText().toString(),
                AESCrypt.encrypt(KEY,
                pass.getText().toString()
            ).toString());
        } catch (GeneralSecurityException e) {
            Log.d(TAG, "onClick: Wrong Key");
        }
        ((PasswordActivity)c).loadView();
    }
}

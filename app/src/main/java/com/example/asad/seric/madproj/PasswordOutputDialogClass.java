package com.example.asad.seric.madproj;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.scottyab.aescrypt.AESCrypt;
import java.security.GeneralSecurityException;
import static android.content.ContentValues.TAG;


public class PasswordOutputDialogClass extends Dialog implements
        android.view.View.OnClickListener {

    String KEY;

    Activity activity;
    Dialog dialog;
    Button delete;
    Passwords data;
    TextView SN, UN, pass;

    public PasswordOutputDialogClass(Activity a, Passwords p, String key) {
        super(a);
        KEY = key;
        this.activity = a;
        this.data = p;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.passwordoutput_dialog);

        delete = findViewById(R.id.btn_yes);
        SN = findViewById(R.id.SN);
        UN = findViewById(R.id.UN);
        pass = findViewById(R.id.Pass);
        delete.setOnClickListener(this);

        SN.setText(data.SiteName);
        UN.setText(data.SiteUsername);
        try{
            pass.setText(
                    AESCrypt.decrypt(
                            KEY,
                            data.SitePassword
                    )
            );

        } catch (GeneralSecurityException e) {
            Log.d(TAG, "onCreate: " + e);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_yes:
                SQLiteHelper s = new SQLiteHelper(getContext());
                s.deletePassword(Integer.toString(data.SiteId));
                ((PasswordActivity) activity).loadView();
                break;
            default:
                break;
        }
        dismiss();
    }
}
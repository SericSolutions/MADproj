package com.example.asad.seric.madproj;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.scottyab.aescrypt.AESCrypt;
import java.security.GeneralSecurityException;

public class LocationActivity extends AppCompatActivity {

    private static final String TAG = "GPS Activity";
    Button button;
    TextView textView, encryptView;
    EditText keyBox;
    LocationManager locationManager;
    LocationListener listener;
    ProgressBar spinner;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        encryptView = findViewById(R.id.encrypted);
        keyBox = findViewById(R.id.KEY);
        button = findViewById(R.id.button);
        spinner = findViewById(R.id.progressBar1);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        spinner.setVisibility(View.GONE);

        encryptView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
               ClipData clip = ClipData.newPlainText("Encrypted Text",
                       encryptView.getText().toString());
               clipboard.setPrimaryClip(clip);
               Toast.makeText(
                       getApplicationContext(),
                       "Text copied to clipboard",
                       Toast.LENGTH_SHORT
               ).show();
           }
        });

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String KEY;
                spinner.setVisibility(View.GONE);
                if(keyBox.getText().toString().length() != 0)
                    KEY = keyBox.getText().toString();
                else {
                    keyBox.setError("Please input a key");
                    return;
                }

                textView.setText(location.getLongitude() +
                        " " + location.getLatitude());

                try {
                    encryptView.setText(
                        AESCrypt.encrypt(
                            KEY,
                            textView.getText().toString()
                        )
                    );
                } catch (GeneralSecurityException e){
                    Log.d(TAG, "onLocationChanged: Encryption problem");
                }
            }


            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {}

            @Override
            public void onProviderEnabled(String s) {}

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }

        };

        configure_button();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                configure_button();
                break;
            default:
                break;
        }
    }

    void configure_button() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
            return;
        }
        // this code won'textView execute IF permissions are not allowed, because in the line above there is return statement.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                spinner.setVisibility(View.VISIBLE);
                try {
                    locationManager.requestLocationUpdates("gps", 5000, 0, listener);
                } catch (SecurityException e) {
                    Log.d(TAG, "onClick: GPS problem");
                }
            }
        });
    }
}
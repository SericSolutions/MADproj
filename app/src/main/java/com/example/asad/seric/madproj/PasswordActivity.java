package com.example.asad.seric.madproj;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class PasswordActivity extends AppCompatActivity {

    String KEY = "1234";

    private static final String TAG = "PasswordActivity";
    List<Passwords> listOfSites = new ArrayList<Passwords>();
    RecyclerView mainRecyclerView;
    MyAdapter myAdapter;
    SQLiteHelper DB = new SQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_activity);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordInputDialogClass cdd
                        = new PasswordInputDialogClass(PasswordActivity.this, KEY);
                cdd.show();
            }
        });

        loadView();
    }

    public void loadView() {
        mainRecyclerView = findViewById(R.id.my_recycler_view);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        listOfSites.clear();
        mainRecyclerView.setAdapter(new MyAdapter(listOfSites, this, KEY));

        Cursor c = DB.getPasswordsOfUser("0");
        for(c.moveToFirst(); c.moveToNext(); c.isAfterLast()){
            listOfSites.add(
                    new Passwords(
                            c.getString(1),
                            c.getString(2),
                            c.getString(3),
                            c.getInt(0),
                            c.getInt(4)
                    )
            );
            Log.d(TAG, "loadView:" + c);
        }
        myAdapter = new MyAdapter(listOfSites, this, KEY);
        mainRecyclerView.setAdapter(myAdapter);
    }
}

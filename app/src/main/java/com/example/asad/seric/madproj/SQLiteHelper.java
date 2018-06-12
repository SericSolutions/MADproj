package com.example.asad.seric.madproj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by asad on 4/3/18.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "keys.db";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("Create table Users(" +
                "UserId integer Primary key," +
                "Name TEXT," +
                "Password TEXT" +
                ");"
        );

        db.execSQL("Create table Notes(" +
                "NoteID integer Primary key," +
                "NoteTitle TEXT," +
                "NoteBody TEXT," +
                "UserId integer," +
                "FOREIGN KEY(UserId) REFERENCES Users(UserId)" +
                ");"
        );

        db.execSQL("Create table Passwords(" +
                "SiteId integer Primary key," +
                "SiteName TEXT," +
                "SiteUsername TEXT," +
                "SitePassword TEXT," +
                "UserId integer," +
                "FOREIGN KEY(UserId) REFERENCES Users(UserId)" +
                ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    // User Resource START
    public void addUser(String name, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        contentValues.put("Password", password);

        db.insert("Users", null, contentValues);
    }

    public void deleteUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Users", "Name = '?'", new String[]{name});
    }

    public Cursor getUsers(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query("Users",
                new String[] {"Name"},
                null,
                null,
                null,
                null,
                null);
        return res;
    }

    public Cursor getUserPassword(String user){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query("Users",
                new String[] {"Password"},
                "Name = '?'",
                new String[] {user},
                null,
                null,
                null);
        return res;
    }
    // User Resource END

    // Note Resource START
    public void addNote(String userId, String noteTitle,
                          String noteBody) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("UserId", userId);
        contentValues.put("NoteTitle", noteTitle);
        contentValues.put("NoteBody", noteBody);

        db.insert("Notes", null, contentValues);
    }

    public void deleteNote(String noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Notes", "NoteId = ?", new String[]{noteId});
    }

    public Cursor getNotesOfUser(String userId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query("Notes",
                null,
                "UserId = ?",
                new String[] {userId},
                null,
                null,
                null);
        return res;
    }
    // Note Resource END

    // Password Resource START
    public void addPassword(int userId, String site,
                        String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("UserId", userId);
        contentValues.put("Site", site);
        contentValues.put("SiteUsername", username);
        contentValues.put("SitePassword", password);

        db.insert("Passwords", null, contentValues);
    }

    public void deletePassword(String siteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Passwords", "SiteId = ?", new String[]{siteId});
    }

    public Cursor getPasswordsOfUser(String userId){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.query("Passwords",
                null,
                "UserId = ?",
                new String[] {userId},
                null,
                null,
                null);
        return res;
    }
    // Password Resource End

}
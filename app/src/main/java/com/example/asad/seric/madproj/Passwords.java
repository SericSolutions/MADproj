package com.example.asad.seric.madproj;

/**
 * Created by asad on 6/17/18.
 *
 * Create table Passwords(
 SiteId integer Primary key,
 SiteName TEXT,
 SiteUsername TEXT,
 SitePassword TEXT,
 UserId integer,
 FOREIGN KEY(UserId) REFERENCES Users(UserId)
 );
 *
 */

public class Passwords {

    public String SiteName, SiteUsername, SitePassword;
    public int SiteId, UserId;

    Passwords(String Name, String Username,
              String Password, int Id, int Uid){
        SiteId = Id;
        UserId = Uid;
        SiteName = Name;
        SiteUsername = Username;
        SitePassword = Password;
    }

}

package com.login.app.data.db.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class LoginHistoryEntity {
    @PrimaryKey(autoGenerate = true) public long id;
    public String uid;
    public String user;
    public String pass;
    public String imei;
    public String date;
}

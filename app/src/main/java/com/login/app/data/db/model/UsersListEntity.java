package com.login.app.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class UsersListEntity {
    @PrimaryKey @NonNull public String uid;
    public String user;
    public String language;
}

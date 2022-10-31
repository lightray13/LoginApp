package com.login.app.data.db;

import com.login.app.data.db.dao.LoginHistoryDao;
import com.login.app.data.db.dao.UsersListDao;
import com.login.app.data.db.model.LoginHistoryEntity;
import com.login.app.data.db.model.UsersListEntity;

import javax.inject.Singleton;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Singleton
@Database(entities = {UsersListEntity.class, LoginHistoryEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UsersListDao usersListDao();
    public abstract LoginHistoryDao loginHistoryDao();
}

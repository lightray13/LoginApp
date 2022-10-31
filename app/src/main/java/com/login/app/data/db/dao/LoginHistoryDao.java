package com.login.app.data.db.dao;

import com.login.app.data.db.model.LoginHistoryEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface LoginHistoryDao {

    @Query("SELECT * FROM loginhistoryentity")
    List<LoginHistoryEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(LoginHistoryEntity loginHistoryEntity);
}

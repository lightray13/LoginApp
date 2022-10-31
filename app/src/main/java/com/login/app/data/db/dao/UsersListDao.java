package com.login.app.data.db.dao;

import com.login.app.data.db.model.UsersListEntity;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface UsersListDao {

    @Query("SELECT * FROM userslistentity")
    List<UsersListEntity> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insert(List<UsersListEntity> list);
}

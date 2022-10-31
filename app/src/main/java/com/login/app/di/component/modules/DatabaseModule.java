package com.login.app.di.component.modules;

import android.content.Context;

import com.login.app.data.db.AppDatabase;
import com.login.app.data.db.AppDbHelper;
import com.login.app.data.db.dao.LoginHistoryDao;
import com.login.app.data.db.dao.UsersListDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module(includes = {ContextModule.class})
public class DatabaseModule {

    @Provides
    @Singleton
    AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    UsersListDao provideUsersListDao(AppDatabase appDatabase) {
        return appDatabase.usersListDao();
    }

    @Provides
    @Singleton
    LoginHistoryDao provideLoginHistoryDao(AppDatabase appDatabase) {
        return appDatabase.loginHistoryDao();
    }

    @Provides
    @Singleton
    AppDbHelper provideDbHelper(UsersListDao usersListDao, LoginHistoryDao loginHistoryDao) {
        return new AppDbHelper(usersListDao, loginHistoryDao);
    }
}

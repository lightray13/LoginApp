package com.login.app.data.db;

import com.login.app.data.db.dao.LoginHistoryDao;
import com.login.app.data.db.dao.UsersListDao;
import com.login.app.data.db.model.LoginHistoryEntity;
import com.login.app.data.db.model.UsersListEntity;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class AppDbHelper{

    private final UsersListDao usersListDao;
    private final LoginHistoryDao loginHistoryDao;

    @Inject
    public AppDbHelper(UsersListDao usersListDao, LoginHistoryDao loginHistoryDao) {
        this.usersListDao = usersListDao;
        this.loginHistoryDao = loginHistoryDao;
    }

    public Single<List<UsersListEntity>> getUsersList() {
        return Single.fromCallable(new Callable<List<UsersListEntity>>() {
            @Override
            public List<UsersListEntity> call() throws Exception {
                return usersListDao.getAll();
            }
        });
    }

    public Single<List<Long>> addUsersList(List<UsersListEntity> users) {
        return Single.fromCallable(new Callable<List<Long>>() {
            @Override
            public List<Long> call() throws Exception {
                return usersListDao.insert(users);
            }
        });
    }

    public Single<List<LoginHistoryEntity>> getLoginHistories() {
        return Single.fromCallable(new Callable<List<LoginHistoryEntity>>() {
            @Override
            public List<LoginHistoryEntity> call() throws Exception {
                return loginHistoryDao.getAll();
            }
        });
    }

    public Single<Long> addLoginHistoryEntity(LoginHistoryEntity loginHistoryEntity) {
        return Single.fromCallable(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return loginHistoryDao.insert(loginHistoryEntity);
            }
        });
    }
}

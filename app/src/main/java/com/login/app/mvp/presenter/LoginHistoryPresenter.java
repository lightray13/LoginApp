package com.login.app.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.login.app.App;
import com.login.app.data.db.AppDbHelper;
import com.login.app.data.db.model.LoginHistoryEntity;
import com.login.app.mvp.view.LoginHistoryView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@InjectViewState
public class LoginHistoryPresenter extends MvpPresenter<LoginHistoryView> {

    @Inject
    AppDbHelper dbHelper;

    public LoginHistoryPresenter() {
        App.getAppComponent().inject(this);
    }

    private Disposable disposable;

    public void loadLoginHistoryList() {
        disposable = dbHelper.getLoginHistories().subscribe(new Consumer<List<LoginHistoryEntity>>() {
            @Override
            public void accept(List<LoginHistoryEntity> loginHistoryEntities) throws Exception {
                getViewState().showLoginHistoryList(loginHistoryEntities);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                getViewState().showError(throwable.getMessage());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disposable.dispose();
    }
}

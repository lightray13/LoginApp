package com.login.app.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.login.app.data.db.model.LoginHistoryEntity;

import java.util.List;

public interface LoginHistoryView extends MvpView {

    void showLoginHistoryList(List<LoginHistoryEntity> list);
    void showError(String error);
}

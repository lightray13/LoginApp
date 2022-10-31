package com.login.app.mvp.view;

import com.arellomobile.mvp.MvpView;
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy;
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType;
import com.login.app.data.db.model.UsersListEntity;

import java.util.List;

@StateStrategyType(AddToEndSingleStrategy.class)
public interface MainView extends MvpView {

    void showUsersList(List<UsersListEntity> list);
    void startLoading();
    void endLoading();
    void showError(String error);
    void showMessage(String message);
    void onLoginResponse();
}

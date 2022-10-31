package com.login.app.mvp.presenter;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.login.app.App;
import com.login.app.data.db.AppDbHelper;
import com.login.app.data.db.model.LoginHistoryEntity;
import com.login.app.data.db.model.UsersListEntity;
import com.login.app.data.network.models.LoginRequest;
import com.login.app.mvp.LoginService;
import com.login.app.data.network.models.ListUser;
import com.login.app.data.network.models.LoginNames;
import com.login.app.mvp.view.MainView;
import com.login.app.utils.RxUtils;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

@InjectViewState
public class MainPresenter extends MvpPresenter<MainView> {

    @Inject
    LoginService loginService;

    @Inject
    AppDbHelper appDbHelper;

    public MainPresenter() {
        App.getAppComponent().inject(this);
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void loadUsersListIntoDatabase(String imei) {
        getViewState().startLoading();
        Observable<LoginNames> observable = RxUtils.wrapRetrofitCall(loginService.getUserList(imei));
        Disposable disposable = RxUtils.wrapAsync(observable)
                .subscribe(new Consumer<LoginNames>() {
                    @Override
                    public void accept(LoginNames loginNames) throws Exception {
                        List<ListUser> users = loginNames.getUsers().getListUsers();
                        List<UsersListEntity> usersListEntities = new ArrayList<>();
                        for (ListUser user: users) {
                            UsersListEntity entity = new UsersListEntity();
                            entity.uid = user.getUid();
                            entity.user = user.getUser();
                            entity.language = user.getLanguage();
                            usersListEntities.add(entity);
                        }
                        compositeDisposable.add(appDbHelper.addUsersList(usersListEntities).subscribe());
                        getViewState().showUsersList(usersListEntities);
                        getViewState().endLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getViewState().showError(throwable.getMessage());
                        getViewState().endLoading();
                    }
                });
        compositeDisposable.add(disposable);
    }

    public void loadUsersList() {
        getViewState().startLoading();
        compositeDisposable.add(appDbHelper.getUsersList().subscribe(new Consumer<List<UsersListEntity>>() {
            @Override
            public void accept(List<UsersListEntity> usersListEntities) throws Exception {
                if (!usersListEntities.isEmpty()) {
                    getViewState().showUsersList(usersListEntities);
                    getViewState().endLoading();
                }
            }
        }));
    }

    public void authentication(String name, String imei, String uid, String pass) {
        getViewState().startLoading();
        Observable<LoginRequest> observable = RxUtils.wrapRetrofitCall(loginService.authentication(imei, uid, pass));
        Disposable disposable = RxUtils.wrapAsync(observable)
                .subscribe(new Consumer<LoginRequest>() {
                    @Override
                    public void accept(LoginRequest loginRequest) throws Exception {
                        if (loginRequest.getCode() == 1022) {
                            LoginHistoryEntity loginHistoryEntity = new LoginHistoryEntity();
                            loginHistoryEntity.uid = uid;
                            loginHistoryEntity.user = name;
                            loginHistoryEntity.pass = pass;
                            loginHistoryEntity.imei = imei;
                            loginHistoryEntity.date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

                            compositeDisposable.add(appDbHelper.addLoginHistoryEntity(loginHistoryEntity).subscribe(new Consumer<Long>() {
                                @Override
                                public void accept(Long aLong) throws Exception {
                                    getViewState().showMessage(name + " logged in");
                                }
                            }));
                            getViewState().endLoading();
                            getViewState().onLoginResponse();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        getViewState().endLoading();
                        getViewState().showError(throwable.getMessage());
                    }
                });
        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}

package com.login.app.di.component;

import android.content.Context;

import com.login.app.di.component.modules.ContextModule;
import com.login.app.di.component.modules.DatabaseModule;
import com.login.app.di.component.modules.LoginModule;
import com.login.app.mvp.presenter.LoginHistoryPresenter;
import com.login.app.mvp.presenter.MainPresenter;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ContextModule.class, LoginModule.class, DatabaseModule.class})
public interface AppComponent {
    Context getContext();

    void inject(MainPresenter mainPresenter);
    void inject(LoginHistoryPresenter mainPresenter);
}

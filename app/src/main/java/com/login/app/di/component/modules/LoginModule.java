package com.login.app.di.component.modules;

import com.login.app.data.network.api.LoginApi;
import com.login.app.mvp.LoginService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(includes = {ApiModule.class})
public class LoginModule {

    @Provides
    @Singleton
    public LoginService provideLoginService(LoginApi loginApi) {
        return new LoginService(loginApi);
    }
}


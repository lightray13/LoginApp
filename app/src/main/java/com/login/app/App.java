package com.login.app;

import android.app.Application;

import com.login.app.di.component.AppComponent;
import com.login.app.di.component.DaggerAppComponent;
import com.login.app.di.component.modules.ContextModule;

public class App extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .contextModule(new ContextModule(this))
                .build();
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
package com.login.app.mvp;

import com.login.app.data.network.api.LoginApi;
import com.login.app.data.network.models.LoginNames;
import com.login.app.data.network.models.LoginRequest;

import retrofit2.Call;

public class LoginService {

    private LoginApi loginApi;

    public LoginService(LoginApi loginApi) {
        this.loginApi = loginApi;
    }

    public Call<LoginNames> getUserList(String imei) {
        return loginApi.getUsersList(imei);
    }

    public Call<LoginRequest> authentication(String imei, String uid, String pass) {
        return loginApi.authentication(imei, uid, pass);
    }
}

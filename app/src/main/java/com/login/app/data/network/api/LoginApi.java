package com.login.app.data.network.api;

import com.login.app.data.network.models.LoginNames;
import com.login.app.data.network.models.LoginRequest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LoginApi {

    @GET("/UKA_TRADE/hs/MobileClient/{IMEI}/form/users")
    Call<LoginNames> getUsersList(
            @Path("IMEI") String imei
    );

    @GET("/UKA_TRADE/hs/MobileClient/{IMEI}/authentication/")
    Call<LoginRequest> authentication(
            @Path("IMEI") String imei,
            @Query("uid") String uid,
            @Query("pass") String pass);
}
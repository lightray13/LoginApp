package com.login.app.data.network.certification;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor implements Interceptor {

    private String username;
    private String password;

    public BasicAuthInterceptor(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String credentials = Credentials.basic(username, password);
        Request request = chain.request();
        request = request.newBuilder().addHeader("Authorization", credentials).build();
        return chain.proceed(request);
    }
}

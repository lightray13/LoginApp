package com.login.app.di.component.modules;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.login.app.data.network.certification.UnsafeOkHttpClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class  RetrofitModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {
        return UnsafeOkHttpClient.getUnsafeOkHttpClient();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Retrofit.Builder builder) {
        return builder.baseUrl("https://dev.sitec24.ru/").build();
    }

    @Provides
    @Singleton
    Retrofit.Builder provideRetrofitBuilder(OkHttpClient okHttpClient, Converter.Factory converterFactory) {
        return new Retrofit.Builder().client(okHttpClient).addConverterFactory(converterFactory);
    }

    @Provides
    @Singleton
    Converter.Factory provideConvertFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder()
                .setLenient()
                .create();
    }

}


package com.qit.android.rest.utils;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class QitApi extends Application {

    private static final String HOST = "http://192.168.1.6:8080";
    private static final String HOST2 = "http://10.238.10.74:8080";
    private static Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    public synchronized static <T> T getApi(Class<T> qitApi) {
        return retrofit.create(qitApi);
    }
}



package com.sample.weatherinfo.common;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/*
AddLoggingInterceptor to enable networking logging to see request response processed by RetroFit
**/

public class AddLoggingInterceptor {

    public static OkHttpClient setLogging(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
    }
}
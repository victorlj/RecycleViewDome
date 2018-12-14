package com.example.day3z_mn.utils;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public class Okutil {
    private OkHttpClient okHttpClient;
    public Okutil() {
        HttpLoggingInterceptor loggingInterceptor =new HttpLoggingInterceptor();
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(20,TimeUnit.SECONDS)
                .callTimeout(20,TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build();
    }

    public static Okutil getInstance(){
        return OkHolder.utils;
    }

    static class OkHolder{
        private static final Okutil utils = new Okutil();
    }

    public void getsync(String murl, Callback callback){
        Request request = new Request.Builder().url(murl).build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}

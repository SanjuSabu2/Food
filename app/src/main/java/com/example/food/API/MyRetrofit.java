package com.example.food.API;

import android.util.Log;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyRetrofit {
    private static retrofit2.Retrofit retrofit;
    private static MyRetrofit myClient;
    public static String BASE_URL = "http://mtest.mobatia.in:8081/";

    public static synchronized MyRetrofit getInstance() {
        if (myClient == null) {
            myClient = new MyRetrofit();
        }
        return myClient;
    }

    public static CartAPI getMyApi(){
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(CartAPI.class);
    }
}

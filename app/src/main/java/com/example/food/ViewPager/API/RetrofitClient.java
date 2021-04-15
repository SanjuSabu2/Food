package com.example.food.ViewPager.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static retrofit2.Retrofit retrofit;
    public static String BASE_URL = "https://mobile.bisad.ae/";


    public static apiInterface getMyApi(){
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(apiInterface.class);
    }
}

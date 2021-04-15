package com.example.food.ViewPager.API;




import com.example.food.ViewPager.Model.MainModel;

import retrofit2.Call;
import retrofit2.http.POST;;

import retrofit.http.FormUrlEncoded;

public interface apiInterface {
    @FormUrlEncoded
//    @POST("api/v1/banner_images")
    @POST("api/v1/banner_images")
    Call<MainModel> getImages();

}

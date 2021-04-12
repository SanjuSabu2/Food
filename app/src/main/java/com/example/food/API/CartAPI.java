package com.example.food.API;

import com.example.food.Model.ProductData;
import com.example.food.Model.ResponseArray;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface CartAPI {
    @FormUrlEncoded
    @POST("api/foodlist")
    Call<ProductData> getProductData(@Field("start") String start,
                                     @Field("limit") String limit);


}

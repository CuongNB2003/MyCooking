package com.devcuong.mycooking.retrofit;

import com.devcuong.mycooking.obj.ListCategory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface MealRetrofitApi {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    MealRetrofitApi mealRetrofitApi = new Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(MealRetrofitApi.class);

    @GET("categories.php")
    Call<ListCategory> getListCategory();

}

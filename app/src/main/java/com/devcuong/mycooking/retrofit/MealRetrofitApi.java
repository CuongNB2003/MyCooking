package com.devcuong.mycooking.retrofit;

import com.devcuong.mycooking.obj.ListCategory;
import com.devcuong.mycooking.obj.ListMeal;
import com.devcuong.mycooking.obj.ListMealCategory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealRetrofitApi {
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    MealRetrofitApi mealRetrofitApi = new Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build().create(MealRetrofitApi.class);

    @GET("categories.php") // liệt kê tất cả thể loại món ăn
    Call<ListCategory> getListCategory();
    @GET("filter.php") // liệt kê các món ăn theo thể loại
    Call<ListMealCategory> getListMealCategory(@Query("c") String nameCategory);
    @GET("lookup.php") // tìm món ăn theo id
    Call<ListMeal> getMeal(@Query("i") String idMeal);
    @GET("random.php") // random 1 món ăn nào đó
    Call<ListMeal> getRandomMeal();
    @GET("filter.php") // liệt món ăn theo khu vực
    Call<ListMealCategory> getListMealByArea(@Query("a") String nameArea);


}

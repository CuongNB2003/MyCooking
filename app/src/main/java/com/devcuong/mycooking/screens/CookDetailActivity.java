package com.devcuong.mycooking.screens;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.devcuong.mycooking.R;
import com.devcuong.mycooking.obj.ListMeal;
import com.devcuong.mycooking.obj.Meal;
import com.devcuong.mycooking.retrofit.MealRetrofitApi;
import com.google.android.material.appbar.AppBarLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CookDetailActivity extends BaseActivity {
    private ImageView imgMeal, imgYoutobe, imgWebsite, imgFavorite;
    private ImageButton imgBack;
    private TextView tvNameToolbar, tvNameImg, tvErrMess, tvCategory, tvArea, tvTags, tvCooking;
    private LinearLayout llMeal;
    private ProgressBar progressBar;
    private AppBarLayout appBarLayout;
    private String idMeal,  youtubeUrl, websiteUrl;
    private List<Meal> meal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_detail);
        anhXa();
        meal = new ArrayList<>();
        imgBack.setOnClickListener(v -> finish());
        idMeal = getIntent().getStringExtra("IdMeal");
        setVisibilityAppBar();
        getMealDetail();
        setOnClickYoutube();
        setOnClickWebsite();

    }
    private void setOnClickWebsite() {
        imgWebsite.setOnClickListener(v -> {
            if (websiteUrl != null && !websiteUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(websiteUrl));
                startActivity(intent);
            } else {
                String strErrMess = getResources().getString(R.string.no_link);
                String strYoutube = getResources().getString(R.string.website);
                Toast.makeText(CookDetailActivity.this, strErrMess + " " + strYoutube, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setOnClickYoutube() {
        imgYoutobe.setOnClickListener(v -> {
            if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(youtubeUrl));
                startActivity(intent);
            } else {
                String strErrMess = getResources().getString(R.string.no_link);
                String strYoutube = getResources().getString(R.string.youtube);
                Toast.makeText(CookDetailActivity.this, strErrMess + " " + strYoutube, Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getMealDetail() {
        progressBar.setVisibility(View.VISIBLE);
        llMeal.setVisibility(View.GONE);
        MealRetrofitApi.mealRetrofitApi.getMeal(idMeal).enqueue(new Callback<ListMeal>() {
            @Override
            public void onResponse(@NonNull Call<ListMeal> call, @NonNull Response<ListMeal> response) {
                assert response.body() != null;
                meal = response.body().getMeals();
                setMeal(meal.get(0));
                progressBar.setVisibility(View.GONE);
                llMeal.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(@NonNull Call<ListMeal> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                llMeal.setVisibility(View.GONE);
                tvErrMess.setVisibility(View.VISIBLE);
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void setMeal(Meal meal) {
        String strCategory = getResources().getString(R.string.category);
        String strArea = getResources().getString(R.string.area);
        String strIngredient = getResources().getString(R.string.ingredient);

        tvNameImg.setText(meal.getStrMeal());
        tvNameToolbar.setText(meal.getStrMeal());
        tvCooking.setText(meal.getStrInstructions());
        tvCategory.setText(strCategory + " " + meal.getStrCategory());
        tvArea.setText(strArea + " " + meal.getStrArea());
        if(meal.getStrTags() == null){
            tvTags.setText(strIngredient + " ");
        }else {
            tvTags.setText(strIngredient + " " + meal.getStrTags());
        }
        Glide.with(this)
                .load(meal.getStrMealThumb())
                .error(R.drawable.ic_category)
                .into(imgMeal);
        youtubeUrl = meal.getStrYoutube();
        websiteUrl = meal.getStrSource();
    }

    private void setVisibilityAppBar() {
        appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                tvNameToolbar.setVisibility(View.VISIBLE);
            } else {
                tvNameToolbar.setVisibility(View.GONE);
            }
        });
    }

    private void anhXa() {
        imgMeal = findViewById(R.id.img_meal);
        imgBack = findViewById(R.id.img_back_meal);
        tvNameImg = findViewById(R.id.tv_nameMeal_img);
        tvNameToolbar = findViewById(R.id.tv_nameMeal_tb);
        appBarLayout = findViewById(R.id.app_bar_layout);
        tvErrMess = findViewById(R.id.tv_err_meal);
        progressBar = findViewById(R.id.pb_meal);
        llMeal = findViewById(R.id.ll_meal);
        tvCategory = findViewById(R.id.tv_category_meal);
        tvArea = findViewById(R.id.tv_area_meal);
        tvTags = findViewById(R.id.tv_tags_meal);
        tvCooking = findViewById(R.id.tv_cooking_meal);
        imgYoutobe = findViewById(R.id.img_youtube);
        imgFavorite = findViewById(R.id.img_favorite);
        imgWebsite = findViewById(R.id.img_website);
    }
}
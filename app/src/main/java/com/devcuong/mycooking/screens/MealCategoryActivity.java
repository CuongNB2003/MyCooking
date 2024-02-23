package com.devcuong.mycooking.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devcuong.mycooking.R;
import com.devcuong.mycooking.adapters.MealCategoryAdapter;
import com.devcuong.mycooking.obj.ListMealCategory;
import com.devcuong.mycooking.obj.MealCategory;
import com.devcuong.mycooking.retrofit.MealRetrofitApi;
import com.devcuong.mycooking.setup.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealCategoryActivity extends BaseActivity {
    private List<MealCategory> mListCategory;
    private String nameCategory;
    private RecyclerView recyMealCategory;
    private TextView tvNameCategory, tvSumMeal;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_category);
        ImageButton btnBack = findViewById(R.id.img_back_mealcategory);
        recyMealCategory = findViewById(R.id.recy_mealcategory);
        tvNameCategory = findViewById(R.id.tv_name_category);
        tvSumMeal = findViewById(R.id.tv_sum_mealcategory);
        nameCategory = getIntent().getStringExtra("nameCategory");
        btnBack.setOnClickListener(v -> finish());

        int spacingInDp = 20;
        float scale = getResources().getDisplayMetrics().density;
        int spacingInPx = (int) (spacingInDp * scale + 0.5f);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MealCategoryActivity.this, 2);
        recyMealCategory.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPx, false));
        recyMealCategory.setLayoutManager(gridLayoutManager);

        mListCategory = new ArrayList<>();
        callApiGetListMealCategory();
    }

    private void callApiGetListMealCategory(){
        recyMealCategory.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
        MealRetrofitApi.mealRetrofitApi.getListMealCategory(nameCategory).enqueue(new Callback<ListMealCategory>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<ListMealCategory> call, @NonNull Response<ListMealCategory> response) {
                recyMealCategory.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
                assert response.body() != null;
                mListCategory = response.body().getMeals();
                tvNameCategory.setText("Name: "+nameCategory);
                tvSumMeal.setText("Sum: "+mListCategory.size());
                MealCategoryAdapter adapter = new MealCategoryAdapter(MealCategoryActivity.this);
                adapter.setData(mListCategory);
                recyMealCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ListMealCategory> call, @NonNull Throwable t) {
                new Handler().postDelayed(() -> {
//                    tvErrMess.setText("Lỗi kết nối :(");
//                    tvErrMess.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);

                }, 500);
            }
        });
    }
}
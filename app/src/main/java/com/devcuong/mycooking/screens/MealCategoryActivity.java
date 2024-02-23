package com.devcuong.mycooking.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.devcuong.mycooking.R;

public class MealCategoryActivity extends BaseActivity {
    private String nameCategory;
    private RecyclerView recyFavorite;
    private TextView tvNameCategory, tvSumMeal;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_category);

        ImageButton btnBack = findViewById(R.id.img_back_mealcategory);
        recyFavorite = findViewById(R.id.recy_mealcategory);
        tvNameCategory = findViewById(R.id.tv_name_category);
        tvSumMeal = findViewById(R.id.tv_sum_mealcategory);

        btnBack.setOnClickListener(v -> finish());

        nameCategory = getIntent().getStringExtra("nameCategory");
        Toast.makeText(this, ""+nameCategory, Toast.LENGTH_SHORT).show();
        tvNameCategory.setText("Name: "+nameCategory);
        tvSumMeal.setText("Sum: "+nameCategory);

    }
}
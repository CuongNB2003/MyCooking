package com.devcuong.mycooking.screens;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.devcuong.mycooking.R;

public class FavoriteActivity extends BaseActivity {
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ImageView btnBack = findViewById(R.id.img_back_favorite);
        RecyclerView recyFavorite = findViewById(R.id.recy_favorite);

        btnBack.setOnClickListener(v -> finish());
    }
}
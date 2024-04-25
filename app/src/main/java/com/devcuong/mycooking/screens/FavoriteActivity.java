package com.devcuong.mycooking.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devcuong.mycooking.R;
import com.devcuong.mycooking.adapters.SearchDishAdapter;
import com.devcuong.mycooking.db.AppDatabase;
import com.devcuong.mycooking.db.FavoriteDao;
import com.devcuong.mycooking.obj.Meal;
import com.devcuong.mycooking.setup.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FavoriteActivity extends BaseActivity {
    private List<Meal> mealList;
    private RecyclerView recyFavorite;
    private final Executor executor = Executors.newSingleThreadExecutor();
    private FavoriteDao favoriteDao;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        ImageButton btnBack = findViewById(R.id.img_back_favorite);
        recyFavorite = findViewById(R.id.recy_favorite);
        favoriteDao = AppDatabase.getDatabase(getApplicationContext()).favoriteDao();
        mealList = new ArrayList<>();
        //setting layout
        int spacingInDp = 20;
        float scale = getResources().getDisplayMetrics().density;
        int spacingInPx = (int) (spacingInDp * scale + 0.5f);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(FavoriteActivity.this, 2);
        recyFavorite.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPx, false));
        recyFavorite.setLayoutManager(gridLayoutManager);
        // lấy dữ liệu
        executor.execute(() -> {
            mealList = favoriteDao.getAllMeals();
            runOnUiThread(() -> {
                SearchDishAdapter adapter = new SearchDishAdapter(FavoriteActivity.this);
                adapter.setData(mealList);
                recyFavorite.setAdapter(adapter);
            });
        });


        btnBack.setOnClickListener(v -> finish());


    }
}
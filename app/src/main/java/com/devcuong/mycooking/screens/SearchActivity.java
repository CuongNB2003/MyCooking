package com.devcuong.mycooking.screens;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devcuong.mycooking.R;
import com.devcuong.mycooking.adapters.SearchDishAdapter;
import com.devcuong.mycooking.adapters.SearchHistoryAdapter;
import com.devcuong.mycooking.obj.ListMeal;
import com.devcuong.mycooking.obj.Meal;
import com.devcuong.mycooking.retrofit.MealRetrofitApi;
import com.devcuong.mycooking.setup.GridSpacingItemDecoration;
import com.devcuong.mycooking.setup.SearchHistory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends BaseActivity {
    private RecyclerView recySearch;
    private List<Meal> mealList;
    private ProgressBar progressBar;
    private TextView tvErrSearch, tvSuggest;
    private LinearLayout linearLayout;
    private ImageView btnBack;
    private SearchView searchView;
    private SearchHistoryAdapter adapter;
    private SearchHistory searchHistory;
    private ListView listViewHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        anhXa();

        mealList = new ArrayList<>();
        searchHistory = new SearchHistory(this);
        adapter = new SearchHistoryAdapter(this, searchHistory.getHistory());
        listViewHistory.setAdapter(adapter);

        int spacingInDp = 20;
        float scale = getResources().getDisplayMetrics().density;
        int spacingInPx = (int) (spacingInDp * scale + 0.5f);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(SearchActivity.this, 2);
        recySearch.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPx, false));
        recySearch.setLayoutManager(gridLayoutManager);

        tvSuggest.setText(getIntent().getStringExtra("nameMeal"));
        linearLayout.setVisibility(View.VISIBLE);

        btnBack.setOnClickListener(v -> finish());
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                linearLayout.setVisibility(View.GONE);
                // Thêm truy vấn vào lịch sử
                searchHistory.addToHistory(query);
                adapter.add(query);
                adapter.notifyDataSetChanged();
                listViewHistory.setAdapter(adapter);
                performSearch(query);

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(searchView.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                linearLayout.setVisibility(View.VISIBLE);
                recySearch.setVisibility(View.GONE);
//                performSearch(newText);
                return true;
            }
        });
        searchView.setOnClickListener(v -> {
            if (v.getId() == R.id.search_view) {
                searchView.setIconified(false);
            }
        });
    }

    private void anhXa() {
        btnBack = findViewById(R.id.img_back_search);
        searchView = findViewById(R.id.search_view);
        recySearch = findViewById(R.id.recy_search);
        progressBar = findViewById(R.id.progress_bar_search);
        tvErrSearch = findViewById(R.id.tv_err_search);
        tvSuggest = findViewById(R.id.tv_suggest);
        linearLayout = findViewById(R.id.ll_food_list);
        listViewHistory = findViewById(R.id.lv_search_history);
    }

    private void performSearch(String query) {
        progressBar.setVisibility(View.VISIBLE);
        recySearch.setVisibility(View.GONE);
        MealRetrofitApi.mealRetrofitApi.searchMeal(query).enqueue(new Callback<ListMeal>() {
            @Override
            public void onResponse(@NonNull Call<ListMeal> call, @NonNull Response<ListMeal> response) {
                progressBar.setVisibility(View.GONE);
                recySearch.setVisibility(View.VISIBLE);
                tvErrSearch.setVisibility(View.GONE);
                assert response.body() != null;
                mealList = response.body().getMeals();
                SearchDishAdapter adapter = new SearchDishAdapter(SearchActivity.this);
                adapter.setData(mealList);
                recySearch.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ListMeal> call, @NonNull Throwable t) {
                new Handler().postDelayed(() -> {
                    recySearch.setVisibility(View.GONE);
                    tvErrSearch.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                }, 500);
            }
        });
    }


}
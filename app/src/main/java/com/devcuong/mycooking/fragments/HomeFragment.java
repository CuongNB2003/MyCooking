package com.devcuong.mycooking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devcuong.mycooking.R;
import com.devcuong.mycooking.adapters.HomeCategoryAdapter;
import com.devcuong.mycooking.adapters.MealCategoryAdapter;
import com.devcuong.mycooking.obj.Category;
import com.devcuong.mycooking.obj.ListCategory;
import com.devcuong.mycooking.obj.ListMeal;
import com.devcuong.mycooking.obj.ListMealCategory;
import com.devcuong.mycooking.obj.Meal;
import com.devcuong.mycooking.obj.MealCategory;
import com.devcuong.mycooking.retrofit.MealRetrofitApi;
import com.devcuong.mycooking.screens.CookDetailActivity;
import com.devcuong.mycooking.screens.SearchActivity;
import com.devcuong.mycooking.setup.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyHomeCategory, recyHomeMeals;
    private ImageView imgHomeMeal, imgSearch;
    private TextView tvErrRandomMeal, tvErrListCategory, tvErrListMeal;
    private ProgressBar progressBarImg, progressBarCategory, progressBarMeal;
    private List<Category> mListCategory;
    private List<Meal> mListRandomMeal;
    private List<MealCategory> mListMeal;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        anhXa(view);

        LinearLayoutManager layoutManagerHomeCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyHomeCategory.setLayoutManager(layoutManagerHomeCategory);

        int spacingInDp = 20;
        float scale = getResources().getDisplayMetrics().density;
        int spacingInPx = (int) (spacingInDp * scale + 0.5f);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        recyHomeMeals.addItemDecoration(new GridSpacingItemDecoration(2, spacingInPx, false));
        recyHomeMeals.setLayoutManager(gridLayoutManager);

        mListCategory = new ArrayList<>();
        mListRandomMeal = new ArrayList<>();
        mListMeal = new ArrayList<>();

        callApiGetListCategory();
        callAPIGetMeal();
        imgHomeMeal.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), CookDetailActivity.class);
            intent.putExtra("IdMeal", mListRandomMeal.get(0).getIdMeal());
            startActivity(intent);
        });
        imgSearch.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            startActivity(intent);
        });
    }

    private void callAPIGetListMeal(String name_area) {
        recyHomeMeals.setVisibility(View.GONE);
        progressBarMeal.setVisibility(View.VISIBLE);
        MealRetrofitApi.mealRetrofitApi.getListMealByArea(name_area).enqueue(new Callback<ListMealCategory>() {
            @Override
            public void onResponse(@NonNull Call<ListMealCategory> call, @NonNull Response<ListMealCategory> response) {
                recyHomeMeals.setVisibility(View.VISIBLE);
                progressBarMeal.setVisibility(View.GONE);
                assert response.body() != null;
                mListMeal = response.body().getMeals();
                MealCategoryAdapter adapter = new MealCategoryAdapter(getActivity());
                adapter.setData(mListMeal);
                recyHomeMeals.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ListMealCategory> call, @NonNull Throwable t) {
                new Handler().postDelayed(() -> {
                    tvErrListMeal.setVisibility(View.VISIBLE);
                    progressBarMeal.setVisibility(View.GONE);
                    recyHomeMeals.setVisibility(View.GONE);
                }, 500);
            }
        });
    }
    private void anhXa(View view) {
        recyHomeCategory = view.findViewById(R.id.recy_home_category);
        recyHomeMeals = view.findViewById(R.id.recy_home_meals);
        imgHomeMeal = view.findViewById(R.id.img_home_meal);
        imgSearch = view.findViewById(R.id.img_search);
        tvErrRandomMeal = view.findViewById(R.id.tv_err_img);
        progressBarImg = view.findViewById(R.id.progress_bar_img);
        tvErrListCategory = view.findViewById(R.id.tv_err_category);
        progressBarCategory = view.findViewById(R.id.progress_bar_category);
        tvErrListMeal = view.findViewById(R.id.tv_err_meals);
        progressBarMeal = view.findViewById(R.id.progress_bar_meals);
    }

    private void callAPIGetMeal() {
        imgHomeMeal.setVisibility(View.GONE);
        progressBarImg.setVisibility(View.VISIBLE);
        MealRetrofitApi.mealRetrofitApi.getRandomMeal().enqueue(new Callback<ListMeal>() {
            @Override
            public void onResponse(@NonNull Call<ListMeal> call, @NonNull Response<ListMeal> response) {
                imgHomeMeal.setVisibility(View.VISIBLE);
                progressBarImg.setVisibility(View.GONE);
                assert response.body() != null;
                mListRandomMeal = response.body().getMeals();
                if (getActivity() != null) {
                    Glide.with(getActivity())
                            .load(mListRandomMeal.get(0).getStrMealThumb())
                            .into(imgHomeMeal);
                }
                callAPIGetListMeal(mListRandomMeal.get(0).getStrArea());
            }

            @Override
            public void onFailure(@NonNull Call<ListMeal> call, @NonNull Throwable t) {
                new Handler().postDelayed(() -> {
                    tvErrRandomMeal.setVisibility(View.VISIBLE);
                    progressBarImg.setVisibility(View.GONE);
                    imgHomeMeal.setVisibility(View.GONE);
                    tvErrListMeal.setVisibility(View.VISIBLE);
                }, 500);
            }
        });
    }
    private void callApiGetListCategory(){
        recyHomeCategory.setVisibility(View.GONE);
        progressBarCategory.setVisibility(View.VISIBLE);
        MealRetrofitApi.mealRetrofitApi.getListCategory().enqueue(new Callback<ListCategory>() {
            @Override
            public void onResponse(@NonNull Call<ListCategory> call, @NonNull Response<ListCategory> response) {
                recyHomeCategory.setVisibility(View.VISIBLE);
                progressBarCategory.setVisibility(View.GONE);
                assert response.body() != null;
                mListCategory = response.body().getCategories();
                HomeCategoryAdapter adapter = new HomeCategoryAdapter(getActivity());
                adapter.setData(mListCategory);
                recyHomeCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ListCategory> call, @NonNull Throwable t) {
                new Handler().postDelayed(() -> {
                    tvErrListCategory.setVisibility(View.VISIBLE);
                    progressBarCategory.setVisibility(View.GONE);
                    recyHomeCategory.setVisibility(View.GONE);
                }, 500);
            }
        });
    }
}
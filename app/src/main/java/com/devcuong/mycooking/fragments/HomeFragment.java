package com.devcuong.mycooking.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devcuong.mycooking.R;
import com.devcuong.mycooking.adapters.HomeCategoryAdapter;
import com.devcuong.mycooking.obj.Category;
import com.devcuong.mycooking.obj.ListCategory;
import com.devcuong.mycooking.obj.ListMeal;
import com.devcuong.mycooking.obj.Meal;
import com.devcuong.mycooking.retrofit.MealRetrofitApi;
import com.devcuong.mycooking.screens.CookDetailActivity;
import com.devcuong.mycooking.screens.SearchActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyHomeCategory, recyHomeMeals;
    private ImageView imgHomeMeal, imgSearch;
    private List<Category> mListCategory;
    private List<Meal> mListRandomMeal;

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
        recyHomeCategory = view.findViewById(R.id.recy_home_category);
        recyHomeMeals = view.findViewById(R.id.recy_home_meals);
        imgHomeMeal = view.findViewById(R.id.img_home_meal);
        imgSearch = view.findViewById(R.id.img_search);

        LinearLayoutManager layoutManagerHomeCategory = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyHomeCategory.setLayoutManager(layoutManagerHomeCategory);
        LinearLayoutManager layoutManagerHomeMeals = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyHomeMeals.setLayoutManager(layoutManagerHomeMeals);

        mListCategory = new ArrayList<>();
        mListRandomMeal = new ArrayList<>();
        
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

    private void callAPIGetMeal() {
        MealRetrofitApi.mealRetrofitApi.getRandomMeal().enqueue(new Callback<ListMeal>() {
            @Override
            public void onResponse(@NonNull Call<ListMeal> call, @NonNull Response<ListMeal> response) {
                assert response.body() != null;
                mListRandomMeal = response.body().getMeals();
                Glide.with(getContext())
                        .load(mListRandomMeal.get(0).getStrMealThumb())
                        .error(R.drawable.ic_category)
                        .into(imgHomeMeal);
            }

            @Override
            public void onFailure(@NonNull Call<ListMeal> call, @NonNull Throwable t) {

            }
        });
    }


    private void callApiGetListCategory(){
//        recyclerView.setVisibility(View.GONE);
//        progressBar.setVisibility(View.VISIBLE);
        MealRetrofitApi.mealRetrofitApi.getListCategory().enqueue(new Callback<ListCategory>() {
            @Override
            public void onResponse(@NonNull Call<ListCategory> call, @NonNull Response<ListCategory> response) {
//                recyclerView.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.GONE);
                assert response.body() != null;
                mListCategory = response.body().getCategories();
                HomeCategoryAdapter adapter = new HomeCategoryAdapter(getActivity());
                adapter.setData(mListCategory);
                recyHomeCategory.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<ListCategory> call, @NonNull Throwable t) {
                new Handler().postDelayed(() -> {
//                    tvErrMess.setVisibility(View.VISIBLE);
//                    progressBar.setVisibility(View.GONE);

                }, 500);
            }
        });
    }
}
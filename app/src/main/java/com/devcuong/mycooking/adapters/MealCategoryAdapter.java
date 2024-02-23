package com.devcuong.mycooking.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devcuong.mycooking.R;

public class MealCategoryAdapter extends RecyclerView.Adapter<MealCategoryAdapter.MealCategoryViewHolder> {
    @NonNull
    @Override
    public MealCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MealCategoryViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MealCategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgMealCategory;
        private final TextView tvNameMealCategory;

        public MealCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMealCategory = itemView.findViewById(R.id.img_category);
            tvNameMealCategory = itemView.findViewById(R.id.tv_category_name);
        }
    }
}

package com.devcuong.mycooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.devcuong.mycooking.R;
import com.devcuong.mycooking.obj.MealCategory;
import com.devcuong.mycooking.screens.CookDetailActivity;

import java.util.List;

public class MealCategoryAdapter extends RecyclerView.Adapter<MealCategoryAdapter.MealCategoryViewHolder> {
    private List<MealCategory> mListMealCategory;
    private final Context mContext;

    public MealCategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<MealCategory> list) {
        this.mListMealCategory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_category, parent, false);
        return new MealCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealCategoryViewHolder holder, int position) {
        MealCategory mealCategory = mListMealCategory.get(position);
        if(mealCategory == null){
            return;
        }
        holder.tvNameMealCategory.setText(mealCategory.getStrMeal());
        Glide.with(mContext)
                .load(mealCategory.getStrMealThumb())
                .error(R.drawable.ic_category)
                .into(holder.imgMealCategory);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CookDetailActivity.class);
            intent.putExtra("IdMeal", mealCategory.getIdMeal());
            mContext.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        if(mListMealCategory != null)
            return mListMealCategory.size();
        return 0;
    }

    public static class MealCategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgMealCategory;
        private final TextView tvNameMealCategory;

        public MealCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMealCategory = itemView.findViewById(R.id.img_meal_category);
            tvNameMealCategory = itemView.findViewById(R.id.tv_meal_category_name);
        }
    }
}

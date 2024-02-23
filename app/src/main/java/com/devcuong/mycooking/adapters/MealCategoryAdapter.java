package com.devcuong.mycooking.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.devcuong.mycooking.R;
import com.devcuong.mycooking.obj.MealCategory;

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
                .asBitmap()
                .load(mealCategory.getStrMealThumb())
                .error(R.drawable.ic_category)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                        roundedBitmapDrawable.setCornerRadius(20f); // thay đổi giá trị này để điều chỉnh bán kính góc bo
                        holder.imgMealCategory.setImageDrawable(roundedBitmapDrawable);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        holder.imgMealCategory.setImageDrawable(placeholder);
                    }
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

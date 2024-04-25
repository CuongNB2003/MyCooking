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
import com.devcuong.mycooking.obj.Meal;
import com.devcuong.mycooking.screens.CookDetailActivity;

import java.util.List;

public class SearchDishAdapter extends RecyclerView.Adapter<SearchDishAdapter.SearchDishViewHolder>{
    private List<Meal> mlistMeal;
    private final Context mContext;

    public SearchDishAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Meal> list) {
        this.mlistMeal = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchDishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meal_category, parent, false);
        return new SearchDishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchDishViewHolder holder, int position) {
        Meal meal = mlistMeal.get(position);
        if(meal == null){
            return;
        }
        holder.tvNameMealCategory.setText(meal.getStrMeal());
        Glide.with(mContext)
                .load(meal.getStrMealThumb())
                .error(R.drawable.ic_category)
                .into(holder.imgMealCategory);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, CookDetailActivity.class);
            intent.putExtra("IdMeal", meal.getIdMeal());
            mContext.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        if(mlistMeal != null)
            return mlistMeal.size();
        return 0;
    }

    public static class SearchDishViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgMealCategory;
        private final TextView tvNameMealCategory;
        public SearchDishViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMealCategory = itemView.findViewById(R.id.img_meal_category);
            tvNameMealCategory = itemView.findViewById(R.id.tv_meal_category_name);
        }
    }
}

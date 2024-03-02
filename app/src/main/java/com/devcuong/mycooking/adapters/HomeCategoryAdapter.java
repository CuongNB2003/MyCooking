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
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.devcuong.mycooking.R;
import com.devcuong.mycooking.obj.Category;
import com.devcuong.mycooking.screens.MealCategoryActivity;

import java.util.List;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {
    private List<Category> mListCategory;
    private final Context mContext;

    public HomeCategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }
    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Category> list) {
        this.mListCategory = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home_category, parent, false);
        return new HomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryViewHolder holder, int position) {
        Category category = mListCategory.get(position);
        if(category == null){
            return;
        }
        holder.tvNameCategory.setText(category.getStrCategory());
        Glide.with(mContext)
                .load(category.getStrCategoryThumb())
                .error(R.drawable.ic_category)
                .transform(new CircleCrop())
                .into(holder.imgCategory);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, MealCategoryActivity.class);
            intent.putExtra("nameCategory", category.getStrCategory());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if(mListCategory != null)
            return mListCategory.size();
        return 0;
    }

    public static class HomeCategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgCategory;
        private final TextView tvNameCategory;
        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_home_category);
            tvNameCategory = itemView.findViewById(R.id.tv_home_category_name);
        }
    }
}

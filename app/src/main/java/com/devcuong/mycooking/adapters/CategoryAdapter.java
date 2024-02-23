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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>{
    private List<Category> mListCategory;
    private final Context mContext;

    public CategoryAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Category> mArrayList) {
        this.mListCategory = mArrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MealCategoryActivity.class);
                intent.putExtra("nameCategory", category.getStrCategory());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        if(mListCategory != null)
            return mListCategory.size();
        return 0;
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgCategory;
        private final TextView tvNameCategory;
        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_category);
            tvNameCategory = itemView.findViewById(R.id.tv_category_name);
        }
    }
}

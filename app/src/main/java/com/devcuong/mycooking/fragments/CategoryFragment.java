package com.devcuong.mycooking.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.devcuong.mycooking.R;
import com.devcuong.mycooking.adapters.CategoryAdapter;
import com.devcuong.mycooking.obj.Category;
import com.devcuong.mycooking.obj.ListCategory;
import com.devcuong.mycooking.retrofit.MealRetrofitApi;
import com.devcuong.mycooking.setup.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private List<Category> mListCategory;
    private  RecyclerView recyclerView;
    private TextView tvErrMess;
    private ProgressBar progressBar;


    public CategoryFragment() {
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
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recy_category);
        tvErrMess = view.findViewById(R.id.tv_error_message);
        progressBar = view.findViewById(R.id.progressBar);
        AHBottomNavigation bottomNavigation = view.findViewById(R.id.bottom_navigation);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3, 30, true));
        recyclerView.setLayoutManager(gridLayoutManager);
        // kiểm tra người dùng để set trạng thái cho AHBottomNavigation
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    // Người dùng đang cuộn xuống, hãy ẩn bottom navigation
                    bottomNavigation.hideBottomNavigation();
                } else if (dy < 0) {
                    // Người dùng đang cuộn lên, hãy hiển thị bottom navigation
                    bottomNavigation.restoreBottomNavigation();
                }
            }
        });

        mListCategory = new ArrayList<>();
        callApiGetListCategory();
    }

    private void updateUIWithCategories(List<Category> categories) {
        // Cập nhật giao diện với dữ liệu từ 'categories'
        // Ví dụ: cập nhật Adapter của RecyclerView
        CategoryAdapter adapter = new CategoryAdapter(getActivity());
        adapter.setData(categories);
        recyclerView.setAdapter(adapter);
    }

    private void callApiGetListCategory(){
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        MealRetrofitApi.mealRetrofitApi.getListCategory().enqueue(new Callback<ListCategory>() {
            @Override
            public void onResponse(@NonNull Call<ListCategory> call, @NonNull Response<ListCategory> response) {
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                assert response.body() != null;
                mListCategory = response.body().getCategories();
                updateUIWithCategories(mListCategory);
            }

            @Override
            public void onFailure(@NonNull Call<ListCategory> call, @NonNull Throwable t) {
                new Handler().postDelayed(() -> {
                    tvErrMess.setText("Lỗi kết nối :(");
                    tvErrMess.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                }, 500);
            }
        });
    }
}

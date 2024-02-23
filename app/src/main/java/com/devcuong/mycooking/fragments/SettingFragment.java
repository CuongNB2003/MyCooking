package com.devcuong.mycooking.fragments;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.devcuong.mycooking.R;
import com.devcuong.mycooking.screens.FavoriteActivity;

public class SettingFragment extends Fragment {
    private AHBottomNavigation bottomNavigation;
    private static final String KEY_STATE = "state";

    // Biến để lưu trạng thái của Fragment
    private String mState;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mState = savedInstanceState.getString(KEY_STATE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout llMyFavorite = view.findViewById(R.id.ll_list_favorite);
        LinearLayout llSendEmail = view.findViewById(R.id.ll_send_mail);
        LinearLayout llCallPhone = view.findViewById(R.id.ll_call_phone);
        LinearLayout llExit = view.findViewById(R.id.ll_exit);
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        Switch switchDarkMode = view.findViewById(R.id.switch_dark_mode);

        switchDarkMode.setChecked(getThemeMode());
        // lắng nghe sự kiện onclick cho switch
        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveThemeMode(isChecked);
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                bottomNavigation = getActivity().findViewById(R.id.bottom_navigation);
                // Đặt vị trí được chọn là 0 (giả sử HomeFragment ở vị trí 0)
                bottomNavigation.setCurrentItem(0);
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                bottomNavigation = getActivity().findViewById(R.id.bottom_navigation);
                // Đặt vị trí được chọn là 0 (giả sử HomeFragment ở vị trí 0)
                bottomNavigation.setCurrentItem(0);
            }
        });

        llCallPhone.setOnClickListener(v -> {
            // Tạo một Intent với hành động ACTION_DIAL
            Intent intent = new Intent(Intent.ACTION_DIAL);
            // Đặt dữ liệu cho Intent là Uri của số điện thoại
            intent.setData(Uri.parse("tel:0339760892"));
            // Kiểm tra xem có ứng dụng nào có thể xử lý Intent này không
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Nếu có, bắt đầu hoạt động
                startActivity(intent);
            }
        });
        llSendEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.putExtra(Intent.EXTRA_SUBJECT, "Chủ đề của email");
            intent.putExtra(Intent.EXTRA_TEXT, "Nội dung của email");
            intent.setData(Uri.parse("mailto:bacuong15423@gmail.com"));
            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        });
        llExit.setOnClickListener(v -> showDialogExit());
        llMyFavorite.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), FavoriteActivity.class);
            startActivity(intent);

        });
    }

    private void showDialogExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Thoát ứng dụng");
        builder.setMessage("Bạn có muốn thoát khỏi ứng dụng không?");
        builder.setPositiveButton("Có", (dialog, id) -> {
            if (getActivity() != null) {
                getActivity().finishAffinity();
            }
        });
        builder.setNegativeButton("Không", (dialog, id) -> dialog.dismiss());
        builder.create().show();

    }

    private void saveThemeMode(boolean isMode) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean("DarkMode", isMode).apply();
    }

    private boolean getThemeMode(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("DarkMode", false);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Lưu trạng thái vào Bundle
        outState.putString(KEY_STATE, mState);
    }
}
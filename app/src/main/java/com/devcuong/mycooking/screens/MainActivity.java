package com.devcuong.mycooking.screens;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.devcuong.mycooking.R;
import com.devcuong.mycooking.fragments.CategoryFragment;
import com.devcuong.mycooking.fragments.HomeFragment;
import com.devcuong.mycooking.fragments.SettingFragment;

public class MainActivity extends BaseActivity {
    private AHBottomNavigation bottomNavigation;
    private boolean checkMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        replaceFragment(new HomeFragment());
        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        checkMode = sharedPreferences.getBoolean("DarkMode", false);
        // tạo item cho bottom
        if(checkMode){
            AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_home, R.drawable.ic_home, R.color.white);
            AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_category, R.drawable.ic_category, R.color.white);
            AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_setting, R.drawable.ic_setting, R.color.white);
            // add item cho bottom
            bottomNavigation.addItem(item1);
            bottomNavigation.addItem(item2);
            bottomNavigation.addItem(item3);
            // cấu hình cho bottom
            bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FF000000"));
            bottomNavigation.setAccentColor(Color.parseColor("#FD960B"));
            bottomNavigation.setInactiveColor(Color.parseColor("#FFFFFFFF"));
            bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE_FORCE);
        }else {
            AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_home, R.drawable.ic_home, R.color.white);
            AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_category, R.drawable.ic_category, R.color.white);
            AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_setting, R.drawable.ic_setting, R.color.white);
            // add item cho bottom
            bottomNavigation.addItem(item1);
            bottomNavigation.addItem(item2);
            bottomNavigation.addItem(item3);
            // cấu hình cho bottom
            bottomNavigation.setAccentColor(Color.parseColor("#FD960B"));
            bottomNavigation.setInactiveColor(Color.parseColor("#FF000000"));
            bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE_FORCE);
        }

        bottomNavigation.setOnTabSelectedListener((position, wasSelected) -> {
            switch (position) {
                case 0:
                    replaceFragment(new HomeFragment());
                    break;
                case 1:
                    replaceFragment(new CategoryFragment());
                    break;
                case 2:
                    replaceFragment(new SettingFragment());
                    break;
                default:
                    break;
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
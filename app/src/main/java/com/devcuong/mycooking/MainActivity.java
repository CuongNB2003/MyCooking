package com.devcuong.mycooking;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.devcuong.mycooking.fragments.CategoryFragment;
import com.devcuong.mycooking.fragments.HomeFragment;
import com.devcuong.mycooking.fragments.SettingFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        replaceFragment(new HomeFragment());

        AHBottomNavigation bottomNavigation = findViewById(R.id.bottom_navigation);

        // tạo item cho bottom
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.tab_home, R.drawable.ic_home, R.color.white);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.tab_category, R.drawable.ic_category, R.color.white);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(R.string.tab_setting, R.drawable.ic_setting, R.color.white);
        // add item cho bottom
        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        // Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.SHOW_WHEN_ACTIVE_FORCE);
//        bottomNavigation.setColored(false);


        bottomNavigation.setAccentColor(Color.parseColor("#FD960B"));
        bottomNavigation.setInactiveColor(Color.parseColor("#00283C"));
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, new HomeFragment()).commit();    //// set default fragment


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
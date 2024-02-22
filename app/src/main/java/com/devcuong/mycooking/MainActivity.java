package com.devcuong.mycooking;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.devcuong.mycooking.R;
import android.os.Bundle;
import com.devcuong.mycooking.databinding.ActivityMainBinding;
import com.devcuong.mycooking.fragments.CategoryFragment;
import com.devcuong.mycooking.fragments.FavoriteFragment;
import com.devcuong.mycooking.fragments.HomeFragment;
import com.devcuong.mycooking.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragmet(new HomeFragment());
        binding.bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.home_nav) {
                replaceFragmet(new HomeFragment());
            } else if (id == R.id.favorite_nav) {
                replaceFragmet(new FavoriteFragment());
            } else if (id == R.id.category_nav) {
                replaceFragmet(new CategoryFragment());
            } else if (id == R.id.setting_nav) {
                replaceFragmet(new SettingFragment());
            }
            return true;
        });
    }

    private void replaceFragmet(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}
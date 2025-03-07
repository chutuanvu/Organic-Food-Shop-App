package com.example.android_btl;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.android_btl.Adapter.viewPage2Adapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminMainActivity extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private  BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity_main);

        viewPager2 = findViewById(R.id.viewPager2);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        viewPage2Adapter viewPage2Adapter = new viewPage2Adapter(this);
        viewPager2.setAdapter(viewPage2Adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    bottomNavigationView.getMenu().findItem(R.id.menu_category).setChecked(true);
                } else if (position == 1) {
                    bottomNavigationView.getMenu().findItem(R.id.menu_item).setChecked(true);
                } else if (position == 2) {
                    bottomNavigationView.getMenu().findItem(R.id.menu_user).setChecked(true);
                } else if (position == 3) {
                    bottomNavigationView.getMenu().findItem(R.id.menu_setting).setChecked(true);
                }
            }

        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.menu_category) {
                viewPager2.setCurrentItem(0);
            } else if (id == R.id.menu_item) {
                viewPager2.setCurrentItem(1);
            } else if (id == R.id.menu_user) {
                viewPager2.setCurrentItem(2);
            } else if (id == R.id.menu_setting) {
                viewPager2.setCurrentItem(3);
            }
            return true;
        });
    }
}
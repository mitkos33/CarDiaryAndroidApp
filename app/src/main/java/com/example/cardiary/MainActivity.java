package com.example.cardiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity {

    boolean isRepairsTab = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle!=null) {
            if (intent.getStringExtra("isRepairs") != null) {
                isRepairsTab = true;
            }
        }

        ViewPager2 viewPager2 = findViewById(R.id.pager);
        ScreenSlidePagerAdapter adapter = new ScreenSlidePagerAdapter(this);
        viewPager2.setAdapter(adapter);
        if (isRepairsTab ==  true){
            viewPager2.setCurrentItem(1);
        }
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    if (position == 0 ) {
                        tab.setText(getString(R.string.car_diary_fuel));
                    }else {
                        tab.setText(getString(R.string.car_diary_repairs));
                    }
                }).attach();
    }


}
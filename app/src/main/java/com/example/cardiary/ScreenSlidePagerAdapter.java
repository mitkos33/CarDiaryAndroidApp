package com.example.cardiary;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.cardiary.fragment.CarDiaryFuelFragment;
import com.example.cardiary.fragment.CarDiaryRepairsFragment;

public class ScreenSlidePagerAdapter extends FragmentStateAdapter {

    public ScreenSlidePagerAdapter(FragmentActivity fa) {

        super(fa);
    }

    private static final int NUM_PAGES = 2;

    @Override
    public Fragment createFragment(int position) {
        if (position == 0 ) {
            return new CarDiaryFuelFragment();
        } else {
            return new CarDiaryRepairsFragment();
        }
    }


    @Override
    public int getItemCount() {
        return NUM_PAGES;
    }

}

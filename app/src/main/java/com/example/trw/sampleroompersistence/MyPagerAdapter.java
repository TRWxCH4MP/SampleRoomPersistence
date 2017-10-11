package com.example.trw.sampleroompersistence;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.trw.sampleroompersistence.fragment.AwardFragment;
import com.example.trw.sampleroompersistence.fragment.HomeFragment;
import com.example.trw.sampleroompersistence.fragment.ManageFragment;

/**
 * Created by TRW on 1/10/2560.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new HomeFragment();
        else if (position == 1)
            return new AwardFragment();
        else if (position == 2)
            return new ManageFragment();
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}

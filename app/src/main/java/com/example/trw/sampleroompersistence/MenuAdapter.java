package com.example.trw.sampleroompersistence;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.trw.sampleroompersistence.fragment.AwardFragment;
import com.example.trw.sampleroompersistence.fragment.HomeFragment;
import com.example.trw.sampleroompersistence.fragment.ManageFragment;

/**
 * Created by TRW on 1/10/2560.
 */

public class MenuAdapter extends FragmentPagerAdapter {
    private Context context;

    public MenuAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
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

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.tab_home);
        } else if (position == 1) {
            return context.getString(R.string.tab_award);
        } else if (position == 2) {
            return context.getString(R.string.tab_manage);
        } else {
            return "";
        }
    }
}

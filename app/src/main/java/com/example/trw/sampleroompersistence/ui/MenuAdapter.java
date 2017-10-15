package com.example.trw.sampleroompersistence.ui;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.trw.sampleroompersistence.R;
import com.example.trw.sampleroompersistence.ui.award.AwardFragment;
import com.example.trw.sampleroompersistence.ui.home.HomeFragment;
import com.example.trw.sampleroompersistence.ui.manage.ManageFragment;

/**
 * Created by TRW on 1/10/2560.
 */

public class MenuAdapter extends FragmentPagerAdapter {
    private static final int PAGE_COUNT = 3;
    private Context context;

    public MenuAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return HomeFragment.newInstance();
        } else if (position == 1) {
            return AwardFragment.newInstance();
        } else if (position == 2) {
            return ManageFragment.newInstance();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
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

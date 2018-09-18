package com.technoface.app.talentscam.Adapters;

/**
 * Created by Ahmet on 25.5.2017.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.technoface.app.talentscam.Fragments.GeneralPlayersFragment;
import com.technoface.app.talentscam.Fragments.TabFragment2;
import com.technoface.app.talentscam.Fragments.RakingFragment;
import com.technoface.app.talentscam.Fragments.SettingsFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                GeneralPlayersFragment tab1 = new GeneralPlayersFragment();

                return tab1;
            case 1:
                TabFragment2 tab2 = new TabFragment2();

                return tab2;
            case 2:
                RakingFragment tab3 = new RakingFragment();

                return tab3;
            case 3:
                SettingsFragment tab4 = new SettingsFragment();

                return tab4;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }



}
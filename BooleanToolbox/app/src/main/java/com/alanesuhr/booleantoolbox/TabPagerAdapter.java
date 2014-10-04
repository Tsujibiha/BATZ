package com.alanesuhr.booleantoolbox;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabPagerAdapter extends FragmentPagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {
        Bundle bundle = new Bundle();
        String tab = "";

        switch (index) {
            case 0:
                tab = "Light";

                break;
            case 1:
                tab = "Indignation";

                break;
            case 2:
                tab = "Judgement";

                break;
        }
        bundle.putString("tab",tab);
        bundle.putInt("int", index);
        SwipeTabFragment swipeTabFragment = new SwipeTabFragment();
        swipeTabFragment.setArguments(bundle);
        return swipeTabFragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
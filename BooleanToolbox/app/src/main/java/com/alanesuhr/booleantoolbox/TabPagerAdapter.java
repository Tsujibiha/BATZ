package com.alanesuhr.booleantoolbox;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by lyndis on 10/3/14.
 */
public class TabPagerAdapter extends FragmentStatePagerAdapterPagerAdapter {
    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = new TabObjectFragment();
        Bundle args = new Bundle();
        //object is just an int
        args.putInt(TabObjectFragment.ARG_OBJECT,i+1);
        fragment.setArguments(args);
        return  fragment;
    }

    @Override
    public int getCount(){
        return 3;
    }

    @Override
    public  CharSequence getPageTitle(int position) {
        return "Object "+ (position+1);
    }

}

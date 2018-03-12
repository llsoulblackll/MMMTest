package com.mmm.mmmtest.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.mmm.mmmtest.R;
import com.mmm.mmmtest.ui.fragment.ListFragment;

public class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public CustomFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public Fragment getItem(int position){
        switch(position){
            case 0:
                return new ListFragment();
            case 1:
                return new Fragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return mContext.getString(R.string.tab_1);
            case 1:
                return mContext.getString(R.string.tab_2);
            default:
                return "";
        }
    }
}

package com.mmm.mmmtest.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mmm.mmmtest.R;
import com.mmm.mmmtest.ui.adapter.CustomFragmentPagerAdapter;

public class TabsFragment extends Fragment {


    View fragmentLayout;

    public TabsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLayout =  inflater.inflate(R.layout.fragment_tabs, container, false);

        ViewPager vp = (ViewPager)fragmentLayout.findViewById(R.id.tabsViewPager);
        Activity act = getActivity();
        vp.setAdapter(new CustomFragmentPagerAdapter(act, this.getChildFragmentManager()/*getActivity().getSupportFragmentManager()*/));
        TabLayout tl = (TabLayout)fragmentLayout.findViewById(R.id.tabs);
        tl.setupWithViewPager(vp);

        return fragmentLayout;
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }
}

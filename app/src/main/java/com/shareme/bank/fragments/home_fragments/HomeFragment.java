package com.shareme.bank.fragments.home_fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.shareme.bank.R;
import com.shareme.bank.fragments.home_fragments.FragmentsAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);

        viewPager = inflate.findViewById(R.id.viewpager);
        tabLayout = inflate.findViewById(R.id.main_tabs);
        tabLayout.setupWithViewPager(viewPager);

        FragmentsAdapter fragmentsAdapter = new FragmentsAdapter(getChildFragmentManager());
        viewPager.setAdapter(fragmentsAdapter);


        return inflate;
    }





}

package com.shareme.bank.fragments.home_fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.shareme.bank.fragments.home_fragments.artical_model.ArticalFragment;
import com.shareme.bank.fragments.home_fragments.ask_donate.AskDonateFragment;


public class FragmentsAdapter extends FragmentPagerAdapter {

    Fragment fragments []= {new ArticalFragment() , new AskDonateFragment()};
    private String title;

    public FragmentsAdapter(FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }


    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            title = "المقالات";
        }
        if (position == 1){
            title = "طلبات التبرع";
        }
        return title;
    }
}

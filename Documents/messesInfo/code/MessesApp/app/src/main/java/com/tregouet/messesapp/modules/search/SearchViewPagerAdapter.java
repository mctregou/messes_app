package com.tregouet.messesapp.modules.search;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by mctregouet on 20/04/2016.
 */
public class SearchViewPagerAdapter extends FragmentStatePagerAdapter {

    private int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created

    private SearchListFragment searchListFragment;
    private SearchMapFragment searchMapFragment;

    public SearchViewPagerAdapter(FragmentManager fm, int mNumbOfTabsumb) {
        super(fm);
        searchListFragment = new SearchListFragment();
        searchMapFragment = new SearchMapFragment();
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return searchListFragment;
        } else {
            return searchMapFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Liste";
        } else {
            return "Carte";
        }
    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }


}

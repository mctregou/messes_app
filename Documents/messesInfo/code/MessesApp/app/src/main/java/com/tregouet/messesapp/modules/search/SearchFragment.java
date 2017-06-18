package com.tregouet.messesapp.modules.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tregouet.messesapp.R;
import com.tregouet.messesapp.modules.main.MainActivity;
import com.tregouet.messesapp.util.slidingtab.SlidingTabLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchFragment extends Fragment {

    @BindView(R.id.pager)
    ViewPager pager;

    @BindView(R.id.tabs)
    SlidingTabLayout tabs;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_search, container, false);

        ButterKnife.bind(this, view);

        initSlidingTabLayout();

        return view;
    }

    private void initSlidingTabLayout() {
        SearchViewPagerAdapter adapter = new SearchViewPagerAdapter(getActivity().getSupportFragmentManager(), 2);
        pager.setAdapter(adapter);
        tabs.setDistributeEvenly(true);
        tabs.setViewPager(pager);
        tabs.setPage(0);
    }

}

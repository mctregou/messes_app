package com.tregouet.messesapp.modules.search;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tregouet.messesapp.R;

import butterknife.ButterKnife;

/**
 * Created by mctregouet on 02/11/2016.
 */

public class SearchMapFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_search_map, container, false);

        ButterKnife.bind(this, view);

        return view;
    }
}

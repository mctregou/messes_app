package com.tregouet.messesapp.modules.filter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tregouet.messesapp.R;
import com.tregouet.messesapp.modules.main.MainActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.cancel)
    public void closeFilter(){
        Log.i(getClass().getSimpleName(), "closeFilter()");
        ((MainActivity)getActivity()).openFilter();
    }

    @OnClick(R.id.validate)
    public void closeFilter2(){
        Log.i(getClass().getSimpleName(), "closeFilter()");
        ((MainActivity)getActivity()).openFilter();
    }
}

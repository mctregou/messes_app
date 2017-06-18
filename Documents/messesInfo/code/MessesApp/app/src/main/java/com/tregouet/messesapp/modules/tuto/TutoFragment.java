package com.tregouet.messesapp.modules.tuto;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tregouet.messesapp.R;
import com.tregouet.messesapp.modules.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class TutoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_tuto, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick(R.id.back)
    public void closeTuto(){
        Log.i(getClass().getSimpleName(), "closeTuto()");
        ((MainActivity)getActivity()).isTutoOpen = false;
        getActivity().onBackPressed();
    }
}

package com.tregouet.messesapp.modules.main;

import android.media.Image;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.tregouet.messesapp.R;
import com.tregouet.messesapp.modules.filter.FilterFragment;
import com.tregouet.messesapp.modules.search.SearchFragment;
import com.tregouet.messesapp.modules.tuto.TutoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    CircleImageView logo;

    @BindView(R.id.search)
    ImageView search;

    public boolean isTutoOpen = false;
    public boolean isFilterOpen = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.frame, new SearchFragment());
        fragmentTransaction.commit();
    }

    @OnClick(R.id.logo)
    public void openTuto(){
        Log.i(getClass().getSimpleName(), "openTuto()");
        if (!isTutoOpen) {
            search.setVisibility(View.GONE);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.frame, new TutoFragment());
            fragmentTransaction.addToBackStack("tuto");
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
            fragmentTransaction.commit();
            isTutoOpen = true;
        } else {
            onBackPressed();
            isTutoOpen = false;
            search.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.search)
    public void openFilter(){
        Log.i(getClass().getSimpleName(), "openTuto()");
        if (!isFilterOpen) {
            search.setVisibility(View.GONE);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.add(R.id.frame, new FilterFragment());
            fragmentTransaction.addToBackStack("filter");
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_up);
            fragmentTransaction.commit();
            isFilterOpen = true;
        } else {
            onBackPressed();
            isFilterOpen = false;
            search.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isTutoOpen){
            search.setVisibility(View.VISIBLE);
            isTutoOpen = false;
        } else if (isFilterOpen){
            search.setVisibility(View.VISIBLE);
            isFilterOpen = false;
        }
    }
}

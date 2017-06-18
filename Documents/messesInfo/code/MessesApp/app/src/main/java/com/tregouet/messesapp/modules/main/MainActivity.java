package com.tregouet.messesapp.modules.main;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;

import com.tregouet.messesapp.R;
import com.tregouet.messesapp.modules.search.SearchFragment;
import com.tregouet.messesapp.modules.tuto.TutoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.logo)
    CircleImageView logo;

    public boolean isTutoOpen = false;

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
        }
    }
}

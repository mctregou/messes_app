package com.tregouet.messesapp.modules.filter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tregouet.messesapp.R;
import com.tregouet.messesapp.model.Event;
import com.tregouet.messesapp.modules.city.CityActivity;
import com.tregouet.messesapp.modules.city.CityEvent;
import com.tregouet.messesapp.modules.city.SendLocationEvent;
import com.tregouet.messesapp.modules.main.MainActivity;
import com.tregouet.messesapp.util.Tools;
import com.tregouet.messesapp.util.dialog.GlobalAlertDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterFragment extends Fragment {

    @BindView(R.id.write_a_city)
    TextView city;

    private SearchQuery searchQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_filter, container, false);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        if (EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @OnClick(R.id.write_a_city)
    public void chooseCity(){
        Log.i(getClass().getSimpleName(), "chooseCity()");
        if (Tools.isNetworkAvailable(getActivity())) {
            if (!city.getText().toString().equals("")) {
                EventBus.getDefault().postSticky(new CityEvent(city.getText().toString()));
            }
            startActivity(new Intent(getActivity(), CityActivity.class));
        } else {
            GlobalAlertDialog dialog = new GlobalAlertDialog(getActivity());
            dialog.setMessage(R.string.no_internet_connexion);
            dialog.show();
        }
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

    @Subscribe(sticky = true)
    public void onCityEvent(SendLocationEvent event){
        city.setText(event.getCity());
        searchQuery.setLatitude(event.getLatitude());
        searchQuery.setLongitude(event.getLongitude());
        searchQuery.setCity(event.getCity());
        searchQuery.setZipcode(event.getZipcode());
        searchQuery.setCountry(event.getCountry());
    }
}

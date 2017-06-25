package com.tregouet.messesapp.modules.city;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.tregouet.messesapp.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class CityActivity extends AppCompatActivity {

    @BindView(R.id.city)
    EditText city;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        ButterKnife.bind(this);

        // Create an instance of GoogleAPIClient.
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onStart() {
        if (googleApiClient != null) {
            googleApiClient.connect();
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        super.onStart();
    }

    @Override
    public void onStop() {
        if (googleApiClient != null) {
            googleApiClient.disconnect();
        }

        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onStop();
    }

    @Subscribe
    public void onCityEvent(CityEvent event){
        city.setText(event.getCity());
    }

    @OnTextChanged(R.id.city)
    public void cityTextChange() {
        System.out.println("textChange");
    }
}

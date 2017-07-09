package com.tregouet.messesapp.modules.city;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tregouet.messesapp.MessesApp;
import com.tregouet.messesapp.R;
import com.tregouet.messesapp.webservice.WebServiceManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CityActivity extends AppCompatActivity {

    @Inject
    WebServiceManager webServiceManager;

    @BindView(R.id.city)
    EditText city;

    @BindView(R.id.cities)
    RecyclerView cities;

    private GoogleApiClient googleApiClient;
    private LinearLayoutManager layoutManager;
    private CitiesListAdapter citiesListAdapter;
    private JsonArray locations = new JsonArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        ButterKnife.bind(this);

        MessesApp.getComponent().inject(this);

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
        Log.i(getClass().getSimpleName(), "cityTextChange");
        searchAddresses(city.getText().toString());
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        initRecyclerView();

        city.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

        city.callOnClick();
    }

    private void initRecyclerView() {
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        cities.setLayoutManager(layoutManager);
        citiesListAdapter = new CitiesListAdapter(this, locations);
        cities.setAdapter(citiesListAdapter);
    }

    private void searchAddresses(String input) {
        Call<JsonObject> call = webServiceManager.getCities(this, input);
        call.enqueue(new Callback<JsonObject>() {
                         @Override
                         public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                             Log.i(getClass().getSimpleName(), "onResponse");
                             System.out.println("status = " + response.body().get("status"));
                             System.out.println("body = " + response.body());
                             System.out.println("body = " + response.getClass());
                             System.out.println("response = " + response.toString());
                             if (response.isSuccessful() && response.body() != null && response.body().get("status") != null && response.body().get("status").getAsString().equals("OK")) {
                                 locations = new JsonArray();
                                 JsonArray jsonArray = response.body().get("predictions").getAsJsonArray();
                                 for (int i = 0; i < jsonArray.size(); i++) {
                                     if (jsonArray.get(i).getAsJsonObject().has("place_id")) {
                                         locations.add(jsonArray.get(i).getAsJsonObject());
                                     }
                                 }
                                 citiesListAdapter.refreshList(locations);
                             }
                         }

                         @Override
                         public void onFailure(Call<JsonObject> call, Throwable t) {
                             Log.i(getClass().getSimpleName(), "onFailure");
                             citiesListAdapter.refreshList(locations);
                         }
                     }

        );
    }

}

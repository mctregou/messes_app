package com.tregouet.messesapp.modules.city;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.tregouet.messesapp.MessesApp;
import com.tregouet.messesapp.R;
import com.tregouet.messesapp.webservice.WebServiceManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mctregouet on 02/11/16.
 */

public class CitiesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    @Inject
    WebServiceManager webServiceManager;

    private JsonArray locations;
    private Activity activity;

    public CitiesListAdapter(Activity activity, JsonArray locations) {
        this.locations = locations;
        this.activity = activity;

        MessesApp.getComponent().inject(this);
    }

    public void refreshList(JsonArray locations) {
        this.locations = locations;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_city, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        JsonObject location = locations.get(position).getAsJsonObject();
        ((CityItemViewHolder) holder).city.setText(location.get("description").getAsString());
        System.out.println("description : " + location.get("description").getAsString());
        System.out.println("place_id : " + location.get("place_id").getAsString());
        ((CityItemViewHolder) holder).setPlaceId(location.get("place_id").getAsString());
    }

    @Override
    public int getItemCount() {
        return (locations == null) ? 0 : locations.size();
    }

    public class CityItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.city)
        TextView city;

        private String placeId;

        public CityItemViewHolder(View inflate) {
            super(inflate);

            ButterKnife.bind(this, itemView);
        }

        public void setPlaceId(String id) {
            placeId = id;
        }

        @OnClick(R.id.background)
        public void chooseCity() {
            Call<JsonObject> call = webServiceManager.getGooglePlaceDetail(activity, placeId);

            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if (response.isSuccessful() && response.body().get("status").getAsString().equals("OK")) {
                        JsonObject result = response.body().get("result").getAsJsonObject();
                        JsonObject location = result.get("geometry").getAsJsonObject().get("location").getAsJsonObject();
                        double latitude = location.get("lat").getAsDouble();
                        double longitude = location.get("lng").getAsDouble();

                        String zipcode = "";
                        String country = "";
                        String address = "";
                        String city = "";


                        JsonArray address_components = result.get("address_components").getAsJsonArray();
                        for (JsonElement element : address_components) {
                            String type = element.getAsJsonObject().get("types").getAsJsonArray().get(0).getAsString();
                            System.out.println("type: " + type);
                            if (type.equals("postal_code")) {
                                zipcode = element.getAsJsonObject().get("long_name").getAsString();
                            } else if (type.equals("street_number")) {
                                address = element.getAsJsonObject().get("long_name").getAsString();
                            } else if (type.equals("route")) {
                                if (!address.equals("")) {
                                    address = address + " " + element.getAsJsonObject().get("long_name").getAsString();
                                } else {
                                    address = element.getAsJsonObject().get("long_name").getAsString();
                                }
                            } else if (type.equals("administrative_area_level_1")) {
                                if (city.equals("")) {
                                    city = element.getAsJsonObject().get("long_name").getAsString();
                                }
                            } else if (type.equals("locality")) {
                                city = element.getAsJsonObject().get("long_name").getAsString();
                            } else if (type.equals("country")) {
                                country = element.getAsJsonObject().get("long_name").getAsString();
                            } else if (type.equals("postal_code")) {
                                zipcode = element.getAsJsonObject().get("long_name").getAsString();
                            }
                        }

                        activity.onBackPressed();


                        EventBus.getDefault().postSticky(new SendLocationEvent(latitude, longitude, zipcode, address, city, country));
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                }
            });

        }
    }
}
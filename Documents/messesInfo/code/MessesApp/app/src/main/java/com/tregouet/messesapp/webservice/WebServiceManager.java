package com.tregouet.messesapp.webservice;

import android.content.Context;

import com.google.gson.JsonObject;
import com.tregouet.messesapp.MessesApp;
import com.tregouet.messesapp.R;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by mctregou on 10/11/16.
 */

@Singleton
public class WebServiceManager {

    @Inject
    ApiClient apiClient;

    @Inject
    GoogleApiClient googleApiClient;


    public WebServiceManager() {
        MessesApp.getComponent().inject(this);
    }

    public Call<ResponseBody> getSchedules(String search) {
        return apiClient.getSchedules(search, "userkey", "json");
    }

    public Call<JsonObject> getCities(Context context, String search) {
        return googleApiClient.getCities(search, context.getString(R.string.google_API_key));
    }

    public Call<JsonObject> getGooglePlaceDetail(Context context, String placeId) {
        return googleApiClient.getGooglePlaceDetail(placeId, context.getString(R.string.google_API_key));
    }
}

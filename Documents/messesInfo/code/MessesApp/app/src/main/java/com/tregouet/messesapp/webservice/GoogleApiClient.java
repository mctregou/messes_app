package com.tregouet.messesapp.webservice;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by mctregouet on 10/11/16.
 */
public interface GoogleApiClient {

    @GET("autocomplete/json")
    Call<JsonObject> getCities(@Query("input") String input, @Query("key") String key);

    @GET("details/json")
    Call<JsonObject> getGooglePlaceDetail(@Query("placeid") String placeid, @Query("key") String key);

}

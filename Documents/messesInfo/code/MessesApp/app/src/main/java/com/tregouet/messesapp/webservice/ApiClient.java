package com.tregouet.messesapp.webservice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mctregouet on 10/11/16.
 */
public interface ApiClient {

    @GET("horaires/{search}")
    Call<ResponseBody> getSchedules(@Path("search") String search, @Field("userkey") String userkey, @Field("format") String format);

}

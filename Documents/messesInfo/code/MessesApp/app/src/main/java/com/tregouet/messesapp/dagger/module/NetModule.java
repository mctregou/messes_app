package com.tregouet.messesapp.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.tregouet.messesapp.webservice.ApiClient;
import com.tregouet.messesapp.webservice.GoogleApiClient;
import com.tregouet.messesapp.webservice.HeaderInterceptor;
import com.tregouet.messesapp.webservice.WebConstants;
import com.tregouet.messesapp.webservice.WebServiceManager;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by mctregouet on 10/11/16.
 */
@Singleton
@Module
public class NetModule {

    private String TAG = "NetModule";

    private Context mContext;

    public NetModule(Context context) {
        this.mContext = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    ApiClient provideApiClient() {
        System.out.println("provideApiClient");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

       /* if (PreferenceManager.getDefaultSharedPreferences(mContext).getString("authenticate_token", null) == null || PreferenceManager.getDefaultSharedPreferences(mContext).getString("uuid", null) == null) {
            return news Retrofit.Builder()
                    .baseUrl(WebConstants.BASE_URL)
                    .client(getClient())
                    .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                    .build()
                    .create(ApiClient.class);
        } else {*/
        return new Retrofit.Builder()
                .baseUrl(WebConstants.BASE_URL)
                .client(getConnectedClient())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build()
                .create(ApiClient.class);
        // }

    }

    @Provides
    @Singleton
    GoogleApiClient provideGoogleApiClient() {
        System.out.println("provideApiClient");

        //ObjectMapper objectMapper = new ObjectMapper();
        //objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return new Retrofit.Builder()
                .baseUrl(WebConstants.BASE_GOOGLE_URL)
                .client(getConnectedClient())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build()
                .create(GoogleApiClient.class);

    }

    private OkHttpClient getConnectedClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(new HeaderInterceptor())
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    @Provides
    @Singleton
    WebServiceManager provideWebServiceManager() {
        return new WebServiceManager();
    }

    @Singleton
    @Provides
    public EventBus provideEventBus() {
        return EventBus.getDefault();
    }
}

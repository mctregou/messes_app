package com.tregouet.messesapp.webservice;

import com.tregouet.messesapp.MessesApp;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xiaohumo on 23/12/2016.
 */

public class HeaderInterceptor implements Interceptor{

    public HeaderInterceptor() {
        MessesApp.getComponent().inject(this);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        if (Locale.getDefault() != null) {
            builder.addHeader("Accept-Language", Locale.getDefault().toString());
        }

        return chain.proceed(builder.build());
    }
}

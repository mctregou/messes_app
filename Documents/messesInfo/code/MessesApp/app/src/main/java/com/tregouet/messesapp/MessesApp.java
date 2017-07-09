package com.tregouet.messesapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.tregouet.messesapp.dagger.component.Component;
import com.tregouet.messesapp.dagger.component.DaggerComponent;
import com.tregouet.messesapp.dagger.module.NetModule;

/**
 * Created by mctregouet on 02/11/2016.
 */

public class MessesApp extends Application {

    protected static MessesApp application;
    protected Component component;

    @Override
    public void onCreate() {
        super.onCreate();

        if (application == null) {
            application = this;
        }

        initComponent();

        component.inject(this);
    }

    @Override
    public void onTerminate() {
        System.out.println("Divercities onTerminate");

        //stopPhoneListener();
        super.onTerminate();
    }


    public void initComponent() {
        this.component = DaggerComponent.builder()
                .netModule(new NetModule(getApplicationContext()))
                .build();
    }


    public static MessesApp getApp() {
        return MessesApp.application;
    }

    public static Component getComponent() {
        return application.component;
    }
}

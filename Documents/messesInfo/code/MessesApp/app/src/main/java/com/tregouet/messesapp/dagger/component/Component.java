package com.tregouet.messesapp.dagger.component;

import com.tregouet.messesapp.MessesApp;
import com.tregouet.messesapp.dagger.module.NetModule;
import com.tregouet.messesapp.modules.city.CitiesListAdapter;
import com.tregouet.messesapp.modules.city.CityActivity;
import com.tregouet.messesapp.webservice.HeaderInterceptor;
import com.tregouet.messesapp.webservice.WebServiceManager;

import javax.inject.Singleton;

/**
 * Created by mctregouet on 10/11/16.
 */
@Singleton
@dagger.Component(modules = {NetModule.class})
public interface Component {

    void inject(MessesApp messesApp);

    void inject(HeaderInterceptor headerInterceptor);

    void inject(WebServiceManager webServiceManager);

    void inject(CityActivity cityActivity);

    void inject(CitiesListAdapter citiesListAdapter);
}

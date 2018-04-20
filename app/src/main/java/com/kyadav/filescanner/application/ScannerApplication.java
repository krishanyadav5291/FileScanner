package com.kyadav.filescanner.application;

import android.app.Application;

import com.kyadav.filescanner.data.DataManager;
import com.kyadav.filescanner.di.component.ApplicationComponent;
import com.kyadav.filescanner.di.component.DaggerApplicationComponent;
import com.kyadav.filescanner.di.module.ApplicationModule;

import javax.inject.Inject;

/**
 * Created by kyadav on 18/04/18.
 */

public class ScannerApplication extends Application {

    ApplicationComponent applicationComponent;

    @Inject
    DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public void setApplicationComponent(ApplicationComponent applicationComponent) {
        this.applicationComponent = applicationComponent;
    }


}

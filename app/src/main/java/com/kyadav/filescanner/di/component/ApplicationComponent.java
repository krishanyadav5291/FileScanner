package com.kyadav.filescanner.di.component;

import android.app.Application;
import android.content.Context;

import com.kyadav.filescanner.application.ScannerApplication;
import com.kyadav.filescanner.data.DataManager;
import com.kyadav.filescanner.di.annotations.ApplicationContext;
import com.kyadav.filescanner.di.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kyadav on 18/04/18.
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(ScannerApplication app);

    @ApplicationContext
    Context context();

    Application application();

    DataManager getDataManager();
}

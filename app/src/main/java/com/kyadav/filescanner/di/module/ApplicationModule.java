package com.kyadav.filescanner.di.module;

import android.app.Application;
import android.content.Context;

import com.kyadav.filescanner.data.AppDataManager;
import com.kyadav.filescanner.data.DataManager;
import com.kyadav.filescanner.di.annotations.ApplicationContext;
import com.kyadav.filescanner.di.annotations.PerActivity;
import com.kyadav.filescanner.ui.main.MainMvpPresenter;
import com.kyadav.filescanner.ui.main.MainMvpView;
import com.kyadav.filescanner.ui.main.MainPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kyadav on 18/04/18.
 */

@Module
public class ApplicationModule {

    private final Application application;


    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    public Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager dataManager) {
        return dataManager;
    }



}

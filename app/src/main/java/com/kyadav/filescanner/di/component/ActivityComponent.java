package com.kyadav.filescanner.di.component;

import com.kyadav.filescanner.di.annotations.PerActivity;
import com.kyadav.filescanner.di.module.ActivityModule;
import com.kyadav.filescanner.ui.main.MainActivity;

import dagger.Component;

/**
 * Created by kyadav on 18/04/18.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);
}

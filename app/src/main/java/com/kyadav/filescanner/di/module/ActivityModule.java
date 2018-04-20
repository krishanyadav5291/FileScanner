package com.kyadav.filescanner.di.module;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kyadav.filescanner.datamodel.FileFrequencyModel;
import com.kyadav.filescanner.di.annotations.ActivityContext;
import com.kyadav.filescanner.di.annotations.PerActivity;
import com.kyadav.filescanner.ui.main.BiggestFileAdapter;
import com.kyadav.filescanner.ui.main.FileFrequencyAdapter;
import com.kyadav.filescanner.ui.main.MainMvpPresenter;
import com.kyadav.filescanner.ui.main.MainMvpView;
import com.kyadav.filescanner.ui.main.MainPresenter;
import com.kyadav.filescanner.util.AppSchedulerProvider;
import com.kyadav.filescanner.util.SchedulerProvider;

import java.io.File;
import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kyadav on 18/04/18.
 */

@Module
public class ActivityModule {

    private AppCompatActivity activity;

    public ActivityModule(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityContext
    Context provideContext() {
        return activity;
    }

    @Provides
    AppCompatActivity provideActivity() {
        return activity;
    }

    @Provides
    CompositeDisposable provideCompositeDisposable() {
        return new CompositeDisposable();
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();

    }

    @Provides
    @PerActivity
    MainMvpPresenter<MainMvpView> provideMainPresenter(MainPresenter<MainMvpView> mainPresenter) {
        return mainPresenter;
    }

    @Provides
    FileFrequencyAdapter provideFrequencyAdapter() {
        return new FileFrequencyAdapter(new ArrayList<FileFrequencyModel>());
    }

    @Provides
    BiggestFileAdapter provideBiggestFileAdapter() {
        return new BiggestFileAdapter(new ArrayList<File>());
    }

    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new GridLayoutManager(activity, 5);
    }
}

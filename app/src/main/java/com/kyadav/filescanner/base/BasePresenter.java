package com.kyadav.filescanner.base;

import com.kyadav.filescanner.data.DataManager;
import com.kyadav.filescanner.util.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by kyadav on 18/04/18.
 */

public class BasePresenter<V extends BaseView> implements MvpPresenter<V> {


    private static final String TAG = "BasePresenter";

    private final DataManager dataManager;
    private final CompositeDisposable compositeDisposable;
    private final SchedulerProvider schedulerProvider;

    private V mvpView;

    @Inject
    public BasePresenter(DataManager dataManager, CompositeDisposable compositeDisposable, SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.compositeDisposable = compositeDisposable;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public void onViewAttach(V view) {
        mvpView = view;

    }

    @Override
    public void onViewDetach() {
        compositeDisposable.dispose();
        mvpView = null;
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public SchedulerProvider getSchedulerProvider() {
        return schedulerProvider;
    }

    public V getMvpView() {
        return mvpView;
    }

    public void setMvpView(V mvpView) {
        this.mvpView = mvpView;
    }
}

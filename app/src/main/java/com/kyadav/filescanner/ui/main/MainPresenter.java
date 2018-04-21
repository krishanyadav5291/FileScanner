package com.kyadav.filescanner.ui.main;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.kyadav.filescanner.base.BasePresenter;
import com.kyadav.filescanner.data.DataManager;
import com.kyadav.filescanner.datamodel.FileFrequencyModel;
import com.kyadav.filescanner.datamodel.FileModel;
import com.kyadav.filescanner.util.SchedulerProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;

/**
 * Created by kyadav on 19/04/18.
 */

public class MainPresenter<V extends MainMvpView> extends BasePresenter<V> implements MainMvpPresenter<V> {

    public static final String TAG = "MainPresenter";

    @Inject
    public MainPresenter(DataManager dataManager, CompositeDisposable compositeDisposable, SchedulerProvider schedulerProvider) {
        super(dataManager, compositeDisposable, schedulerProvider);
    }

    @Override
    public void onStartScanClicked() {
        getMvpView().showNotification();
        getMvpView().showLoading();
        getCompositeDisposable()
                .add(getDataManager().getFilesFromExternalStorage()
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<File>>() {
                            @Override
                            public void accept(List<File> fileList) throws Exception {
                                getMvpView().hideLoading();
                                onScanCompleted(fileList);


                            }


                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getMvpView().hideLoading();

                            }
                        }));

    }

    @Override
    public void onScanCompleted(final List<File> fileList) {

        getMvpView().showLoading();

        getCompositeDisposable()
                .add(getDataManager().getAverageFileSize(fileList)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<Double>() {
                            @Override
                            public void accept(Double aDouble) throws Exception {
                                getMvpView().updateNotification("Scan Completed");
                                getMvpView().hideLoading();
                                getMvpView().onAverageSizeCalculated(aDouble);
                                calculateFrequency(fileList);

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getMvpView().hideLoading();

                            }
                        }));


    }


    @Override
    public void onStopScanClicked() {

    }

    @Override
    public void calculateFrequency(final List<File> fileList) {
        getMvpView().showLoading();

        getCompositeDisposable()
                .add(getDataManager().getMostFrequencyFile(fileList)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<FileFrequencyModel>>() {
                            @Override
                            public void accept(List<FileFrequencyModel> list) throws Exception {
                                getMvpView().hideLoading();
                                getMvpView().onFrequencyCalculated(list);
                                calculateBiggestFiles(fileList);


                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getMvpView().hideLoading();

                            }
                        }));

    }

    @Override
    public void calculateBiggestFiles(List<File> fileList) {
        getMvpView().showLoading();
        getCompositeDisposable()
                .add(getDataManager().getBiggestFiles(fileList)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().ui())
                        .subscribe(new Consumer<List<File>>() {
                            @Override
                            public void accept(List<File> fileList) throws Exception {
                                getMvpView().hideLoading();
                                getMvpView().onBiggestFilesCalculated(fileList);

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                getMvpView().hideLoading();

                            }
                        }));

    }
}

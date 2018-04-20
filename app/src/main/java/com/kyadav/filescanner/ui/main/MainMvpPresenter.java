package com.kyadav.filescanner.ui.main;

import com.kyadav.filescanner.base.MvpPresenter;

import java.io.File;
import java.util.List;

/**
 * Created by kyadav on 19/04/18.
 */

public interface MainMvpPresenter<V extends MainMvpView> extends MvpPresenter<V> {
    public void onStartScanClicked();
    public void onScanCompleted(List<File> fileList);
    public void onStopScanClicked();
    public void calculateFrequency(List<File> fileList);
    public void calculateBiggestFiles(List<File> fileList);
}

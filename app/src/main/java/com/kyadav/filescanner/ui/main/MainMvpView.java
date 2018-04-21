package com.kyadav.filescanner.ui.main;

import com.kyadav.filescanner.base.BaseView;
import com.kyadav.filescanner.datamodel.FileFrequencyModel;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by kyadav on 19/04/18.
 */

public interface MainMvpView extends BaseView {
    void onAverageSizeCalculated(double size);

    void onFrequencyCalculated(List<FileFrequencyModel> list);

    void onBiggestFilesCalculated(List<File> fileList);

    void showNotification();

    void updateNotification(String text);

}

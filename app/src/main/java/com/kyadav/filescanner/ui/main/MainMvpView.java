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
    public void onAverageSizeCalculated(double size);
    public void onFrequencyCalculated(List<FileFrequencyModel> list);
    public void onBiggestFilesCalculated(List<File> fileList);
}

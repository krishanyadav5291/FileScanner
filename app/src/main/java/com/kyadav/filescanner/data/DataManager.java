package com.kyadav.filescanner.data;

import com.kyadav.filescanner.datamodel.FileFrequencyModel;
import com.kyadav.filescanner.datamodel.FileModel;

import java.io.File;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;


/**
 * Created by kyadav on 18/04/18.
 */

public interface DataManager {

    Observable<List<File>> getFilesFromExternalStorage();

    public Observable<Double> getAverageFileSize(List<File> fileList);

    public Observable<List<FileFrequencyModel>> getMostFrequencyFile(List<File> fileList);

    public Observable<List<File>> getBiggestFiles(List<File> fileList);
}

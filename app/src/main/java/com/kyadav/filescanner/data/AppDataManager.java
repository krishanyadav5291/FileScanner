package com.kyadav.filescanner.data;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.webkit.MimeTypeMap;

import com.kyadav.filescanner.datamodel.FileFrequencyModel;
import com.kyadav.filescanner.datamodel.FileModel;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by kyadav on 18/04/18.
 */

public class AppDataManager implements DataManager {

    List<File> list = new ArrayList<>();
    double fileSize = 0;

    public static final String TAG = "DataManager";

    @Inject
    public AppDataManager() {

    }

    @Override
    public Observable<List<File>> getFilesFromExternalStorage() {
        list.clear();
        final File dir = new File(Environment.getExternalStorageDirectory().toString());
        return Observable.create(new ObservableOnSubscribe<List<File>>() {
            @Override
            public void subscribe(ObservableEmitter<List<File>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(getAllFiles(dir));
                    e.onComplete();
                }

            }
        });


    }

    @Override
    public Observable<Double> getAverageFileSize(final List<File> fileList) {
        return Observable.create(new ObservableOnSubscribe<Double>() {
            @Override
            public void subscribe(ObservableEmitter<Double> e) throws Exception {

                if (!e.isDisposed()) {
                    e.onNext(getAverage(fileList));
                    e.onComplete();
                }

            }
        });

    }

    @Override
    public Observable<List<FileFrequencyModel>> getMostFrequencyFile(final List<File> fileList) {
        return Observable.create(new ObservableOnSubscribe<List<FileFrequencyModel>>() {
            @Override
            public void subscribe(ObservableEmitter<List<FileFrequencyModel>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(getFrequency(fileList));
                    e.onComplete();
                }

            }
        });
    }

    @Override
    public Observable<List<File>> getBiggestFiles(final List<File> fileList) {
        return Observable.create(new ObservableOnSubscribe<List<File>>() {
            @Override
            public void subscribe(ObservableEmitter<List<File>> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(getBiggestFileList(fileList));
                    e.onComplete();
                }

            }
        });
    }


    public List<File> getBiggestFileList(List<File> fileList) {
        List<File> list = new ArrayList<>();
        Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                return (int) (o2.length() - o1.length());
            }
        });

        for (int i = 0; i < 10; i++) {
            list.add(fileList.get(i));
        }
        return list;

    }


    private List<File> getAllFiles(File directory) {
        File[] filesList = directory.listFiles();

        for (int i = 0; i < filesList.length; i++) {
            if (!filesList[i].isDirectory()) {
                list.add(filesList[i]);
                fileSize = fileSize + filesList[i].length();
            } else {
                getAllFiles(filesList[i]);

            }
        }


        return list;

    }

    private double getAverage(List<File> fileList) {
        double fileSize = 0;


        for (int i = 0; i < fileList.size(); i++) {
            fileSize = fileSize + fileList.get(i).length();

        }

        double averageSize = fileSize / fileList.size();
        double averageSizeInMB = averageSize / 1048576;
        return averageSizeInMB;
    }

    public List<FileFrequencyModel> getFrequency(List<File> fileList) {
        Map<String, Integer> map = new LinkedHashMap<String, Integer>();
        for (File file : fileList) {
            // String extension = " kry "+file.getAbsolutePath().toString().substring(file.getAbsoluteFile().toString().lastIndexOf("."));
            Uri selectedUri = Uri.fromFile(file);
            String extension = MimeTypeMap.getFileExtensionFromUrl(selectedUri.toString());
            Log.d(TAG, " EXT " + extension);
            if (map.containsKey(extension)) {
                map.put(extension, map.get(extension) + 1);
            } else {
                map.put(extension, 1);
            }

            Log.d(TAG, " map " + map.get(extension));

        }

        Log.d(TAG, "HERE");


        List<Map.Entry<String, Integer>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        Log.d(TAG, "HERE");

        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        int sizeOfMap = 0;
        for (Map.Entry<String, Integer> entry : list) {
            if (sizeOfMap >= 5) {
                break;
            }
            sortedMap.put(entry.getKey(), entry.getValue());
            if (entry.getKey() != " " && entry.getKey() != "0")
                sizeOfMap++;

        }

        List<FileFrequencyModel> frequencyModelList = new ArrayList<>();


        for (Map.Entry<String, Integer> entry : sortedMap.entrySet()) {
            FileFrequencyModel fileFrequencyModel = new FileFrequencyModel();
            fileFrequencyModel.setFileExtension(entry.getKey());
            fileFrequencyModel.setFrequency(String.valueOf(entry.getValue()));
            frequencyModelList.add(fileFrequencyModel);


        }
        return frequencyModelList;
    }


}

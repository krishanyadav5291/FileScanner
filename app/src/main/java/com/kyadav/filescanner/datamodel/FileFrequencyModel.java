package com.kyadav.filescanner.datamodel;

/**
 * Created by kyadav on 20/04/18.
 */

public class FileFrequencyModel {

    private String fileExtension;
    private String frequency;

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}

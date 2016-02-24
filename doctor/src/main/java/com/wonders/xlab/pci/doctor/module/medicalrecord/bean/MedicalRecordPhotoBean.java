package com.wonders.xlab.pci.doctor.module.medicalrecord.bean;

import java.util.ArrayList;

/**
 * Created by hua on 16/2/24.
 */
public class MedicalRecordPhotoBean extends MedicalRecordBean {
    private String timeStr;
    private String title;
    private ArrayList<String> photos;

    @Override
    public int getItemLayout() {
        return ITEM_LAYOUT_PHOTO;
    }

    public String getTimeStr() {
        return timeStr;
    }

    public void setTimeStr(String timeStr) {
        this.timeStr = timeStr;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<String> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<String> photos) {
        this.photos = photos;
    }
}

package com.wonders.xlab.pci.doctor.module.chatroom.medicalrecord.bean;

import java.util.ArrayList;

/**
 * Created by hua on 16/2/24.
 */
public class MedicalRecordPhotoBean extends MedicalRecordBean {
    private String timeStr;
    private String title;
    private ArrayList<String> photoThumbnails;
    private ArrayList<String> photosOrigin;

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

    public ArrayList<String> getPhotoThumbnails() {
        return photoThumbnails;
    }

    public void setPhotoThumbnails(ArrayList<String> photoThumbnails) {
        this.photoThumbnails = photoThumbnails;
    }

    public ArrayList<String> getPhotosOrigin() {
        return photosOrigin;
    }

    public void setPhotosOrigin(ArrayList<String> photosOrigin) {
        this.photosOrigin = photosOrigin;
    }
}

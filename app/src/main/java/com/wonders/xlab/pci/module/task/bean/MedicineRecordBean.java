package com.wonders.xlab.pci.module.task.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hua on 15/12/16.
 */
public class MedicineRecordBean implements Parcelable {
    private String mMedicineName;
    private String mMedicineDosage;

    public MedicineRecordBean(String medicineName, String medicineDosage) {
        mMedicineName = medicineName;
        mMedicineDosage = medicineDosage;
    }

    public String getMedicineName() {
        return mMedicineName;
    }

    public void setMedicineName(String medicineName) {
        mMedicineName = medicineName;
    }

    public String getMedicineDosage() {
        return mMedicineDosage;
    }

    public void setMedicineDosage(String medicineDosage) {
        mMedicineDosage = medicineDosage;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mMedicineName);
        dest.writeString(this.mMedicineDosage);
    }

    protected MedicineRecordBean(Parcel in) {
        this.mMedicineName = in.readString();
        this.mMedicineDosage = in.readString();
    }

    public static final Creator<MedicineRecordBean> CREATOR = new Creator<MedicineRecordBean>() {
        public MedicineRecordBean createFromParcel(Parcel source) {
            return new MedicineRecordBean(source);
        }

        public MedicineRecordBean[] newArray(int size) {
            return new MedicineRecordBean[size];
        }
    };
}

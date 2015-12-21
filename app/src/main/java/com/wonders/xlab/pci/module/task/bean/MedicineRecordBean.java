package com.wonders.xlab.pci.module.task.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hua on 15/12/16.
 */
public class MedicineRecordBean implements Parcelable {
    private String medicineId;
    private String medicineName;
    private String medicineDosage;
    private boolean isChecked;

    public MedicineRecordBean(String medicineId,String medicineName, String medicineDosage) {
        this.medicineId = medicineId;
        this.medicineName = medicineName;
        this.medicineDosage = medicineDosage;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getMedicineDosage() {
        return medicineDosage;
    }

    public void setMedicineDosage(String medicineDosage) {
        this.medicineDosage = medicineDosage;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.medicineId);
        dest.writeString(this.medicineName);
        dest.writeString(this.medicineDosage);
        dest.writeByte(isChecked ? (byte) 1 : (byte) 0);
    }

    protected MedicineRecordBean(Parcel in) {
        this.medicineId = in.readString();
        this.medicineName = in.readString();
        this.medicineDosage = in.readString();
        this.isChecked = in.readByte() != 0;
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

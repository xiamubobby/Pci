package com.wonders.xlab.patient.module.main.home.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hua on 16/3/11.
 */
public class HomeTopCircleBean implements Parcelable {
    private String title;
    private String value;
    private String unit;

    public HomeTopCircleBean(String title, String value, String unit) {
        this.title = title;
        this.value = value;
        this.unit = unit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.value);
        dest.writeString(this.unit);
    }

    protected HomeTopCircleBean(Parcel in) {
        this.title = in.readString();
        this.value = in.readString();
        this.unit = in.readString();
    }

    public static final Creator<HomeTopCircleBean> CREATOR = new Creator<HomeTopCircleBean>() {
        public HomeTopCircleBean createFromParcel(Parcel source) {
            return new HomeTopCircleBean(source);
        }

        public HomeTopCircleBean[] newArray(int size) {
            return new HomeTopCircleBean[size];
        }
    };
}

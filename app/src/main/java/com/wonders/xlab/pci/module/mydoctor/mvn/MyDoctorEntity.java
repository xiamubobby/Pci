package com.wonders.xlab.pci.module.mydoctor.mvn;

import com.wonders.xlab.pci.module.base.mvn.entity.BaseEntity;

import java.util.List;

/**
 * Created by hua on 16/1/28.
 */
public class MyDoctorEntity extends BaseEntity {
    private List<DoctorInfoEntity> mDoctorInfoEntityList;

    public List<DoctorInfoEntity> getDoctorInfoEntityList() {
        return mDoctorInfoEntityList;
    }

    public void setDoctorInfoEntityList(List<DoctorInfoEntity> doctorInfoEntityList) {
        mDoctorInfoEntityList = doctorInfoEntityList;
    }
}

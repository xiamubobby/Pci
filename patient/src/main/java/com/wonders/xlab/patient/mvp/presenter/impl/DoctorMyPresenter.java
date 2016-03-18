package com.wonders.xlab.patient.mvp.presenter.impl;

import com.wonders.xlab.patient.Constant;
import com.wonders.xlab.patient.module.doctors.adapter.bean.MyDoctorItemBean;
import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;
import com.wonders.xlab.patient.mvp.model.IDoctorMyModel;
import com.wonders.xlab.patient.mvp.model.impl.DoctorMyModel;
import com.wonders.xlab.patient.mvp.presenter.IDoctorMyPresenter;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;

/**
 * Created by hua on 16/3/14.
 */
public class DoctorMyPresenter extends BasePresenter implements IDoctorMyPresenter, DoctorMyModel.DoctorMyModelListener {
    private DoctorMyPresenterListener mDoctorMyListener;
    private IDoctorMyModel mDoctorMyModel;

    public DoctorMyPresenter(DoctorMyPresenterListener doctorMyListener) {
        mDoctorMyListener = doctorMyListener;

        mDoctorMyModel = new DoctorMyModel(this);
        addModel(mDoctorMyModel);
    }

    @Override
    public void getMyDoctors(String patientId) {
        mDoctorMyModel.getMyDoctors(patientId);
    }

    @Override
    public void onReceiveMyDoctorListSuccess(DoctorMyEntity.RetValuesEntity valuesEntity) {
        mDoctorMyListener.hideLoading();

        ArrayList<MyDoctorItemBean> doctorItemBeanArrayList = new ArrayList<>();

        List<DoctorMyEntity.RetValuesEntity.ServiceTrueEntity> inServiceEntityList = valuesEntity.getServiceTrue();
        for (DoctorMyEntity.RetValuesEntity.ServiceTrueEntity entity : inServiceEntityList) {
            MyDoctorItemBean itemBean = new MyDoctorItemBean();
            itemBean.setHeaderId(MyDoctorItemBean.HEADER_ID_IN_SERVICE);
            itemBean.setGroupId(entity.getImGroupId());
            itemBean.setDoctorGroupName(entity.getName());
            itemBean.setLatestChatMessage(entity.getContent());
            itemBean.setTimeStr(entity.getTimeExp());
            itemBean.setPortraitUrl(entity.getAvatars() != null && entity.getAvatars().size() > 0 ? entity.getAvatars().get(0) : Constant.DEFAULT_PORTRAIT);

            doctorItemBeanArrayList.add(itemBean);
        }

        DoctorMyEntity.RetValuesEntity.ServiceFalseEntity serviceFalseEntityList = valuesEntity.getServiceFalse();
        List<DoctorMyEntity.RetValuesEntity.ServiceFalseEntity.ContentEntity> contentEntityList = serviceFalseEntityList.getContent();
        for (DoctorMyEntity.RetValuesEntity.ServiceFalseEntity.ContentEntity entity : contentEntityList) {
            MyDoctorItemBean itemBean = new MyDoctorItemBean();
            itemBean.setHeaderId(MyDoctorItemBean.HEADER_ID_OUT_OF_SERVICE);
            itemBean.setImGroupId(entity.getImGroupId());
            itemBean.setGroupId(entity.getDoctorGroupId());
            itemBean.setDoctorGroupName(entity.getName());
            itemBean.setLatestChatMessage(entity.getContent());
            itemBean.setTimeStr(entity.getTimeExp());
            itemBean.setPortraitUrl(entity.getAvatars() != null && entity.getAvatars().size() > 0 ? entity.getAvatars().get(0) : Constant.DEFAULT_PORTRAIT);

            doctorItemBeanArrayList.add(itemBean);
        }

        mDoctorMyListener.showMyDoctorList(doctorItemBeanArrayList);
    }

    @Override
    public void onReceiveFailed(String message) {
        mDoctorMyListener.hideLoading();
        mDoctorMyListener.showError(message);
    }

    public interface DoctorMyPresenterListener extends BasePresenterListener {
        void showMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);

        void appendMyDoctorList(ArrayList<MyDoctorItemBean> myDoctorBeanList);
    }
}

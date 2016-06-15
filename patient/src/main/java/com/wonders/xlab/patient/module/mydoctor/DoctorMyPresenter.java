package com.wonders.xlab.patient.module.mydoctor;

import com.wonders.xlab.patient.module.mydoctor.adapter.MyDoctorItemBean;
import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by hua on 16/3/14.
 */
public class DoctorMyPresenter extends BasePagePresenter implements DoctorMyContract.Presenter, DoctorMyContract.Callback {
    private DoctorMyContract.ViewListener mViewListener;
    private DoctorMyContract.Model mDoctorMyModel;

    @Inject
    public DoctorMyPresenter(DoctorMyModel doctorMyModel, DoctorMyContract.ViewListener viewListener) {
        mDoctorMyModel = doctorMyModel;
        mViewListener = viewListener;
        addModel(mDoctorMyModel);
    }

    @Override
    public void getMyDoctors(String patientId, boolean isRefresh) {
        if (isRefresh) {
            mViewListener.showLoading("");
            resetPageInfo();
        }
        if (mIsLast) {
            mViewListener.hideLoading();
            mViewListener.showReachTheLastPageNotice("");
            return;
        }
        mDoctorMyModel.getMyDoctors(patientId, getNextPageIndex(), DEFAULT_PAGE_SIZE, this);
    }

    @Override
    public void onReceiveMyDoctorListSuccess(DoctorMyEntity.RetValuesEntity valuesEntity) {
        mViewListener.hideLoading();

        DoctorMyEntity.RetValuesEntity.ServiceFalseEntity serviceFalse = valuesEntity.getServiceFalse();
        if (null == serviceFalse) {
            return;
        }
        updatePageInfo(serviceFalse.getNumber(), serviceFalse.isFirst(), serviceFalse.isLast());

        ArrayList<MyDoctorItemBean> doctorItemBeanArrayList = new ArrayList<>();

        List<DoctorMyEntity.RetValuesEntity.ServiceTrueEntity> inServiceEntityList = valuesEntity.getServiceTrue();
        for (DoctorMyEntity.RetValuesEntity.ServiceTrueEntity entity : inServiceEntityList) {
            MyDoctorItemBean itemBean = new MyDoctorItemBean();
            itemBean.setType(MyDoctorItemBean.TYPE_IN_SERVICE);
            itemBean.setOwnerId(entity.getOwnerId());
            itemBean.setImGroupId(entity.getImGroupId());
            itemBean.setHospitalName(entity.getHospitalName());
            Date date = new Date(entity.getExpirationTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String deadline = format.format(date);

            itemBean.setDeadline(deadline);
            itemBean.setDoctorGroupName(entity.getName());
            itemBean.setLatestChatMessage(entity.getContent());
            itemBean.setTimeStr(entity.getTimeExp());
            itemBean.setPortraitUrl(entity.getAvatars());

            doctorItemBeanArrayList.add(itemBean);
        }

        DoctorMyEntity.RetValuesEntity.ServiceFalseEntity serviceFalseEntityList = serviceFalse;
        List<DoctorMyEntity.RetValuesEntity.ServiceFalseEntity.ContentEntity> contentEntityList = serviceFalseEntityList.getContent();
        for (DoctorMyEntity.RetValuesEntity.ServiceFalseEntity.ContentEntity entity : contentEntityList) {
            MyDoctorItemBean itemBean = new MyDoctorItemBean();
            itemBean.setType(MyDoctorItemBean.TYPE_OUT_OF_SERVICE);
            itemBean.setImGroupId(entity.getImGroupId());
            itemBean.setOwnerId(entity.getOwnerId());
            itemBean.setHospitalName(entity.getHospitalName());

            Date date = new Date(entity.getExpirationTime());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String deadline = format.format(date);

            itemBean.setDeadline(deadline);
            itemBean.setDoctorGroupName(entity.getName());
            itemBean.setLatestChatMessage(entity.getContent());
            itemBean.setTimeStr(entity.getTimeExp());
            itemBean.setPortraitUrl(entity.getAvatars());

            doctorItemBeanArrayList.add(itemBean);
        }

        if (shouldAppend()) {
            if (doctorItemBeanArrayList.size() <= 0) {
                mViewListener.showReachTheLastPageNotice("没有更多数据了");
                return;
            }
            mViewListener.appendMyDoctorList(doctorItemBeanArrayList);
        } else {
            if (doctorItemBeanArrayList.size() <= 0) {
                mViewListener.showEmptyView("");
                return;
            }
            mViewListener.showMyDoctorList(doctorItemBeanArrayList);
        }
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        showError(mViewListener, code, message);
    }
}

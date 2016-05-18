package com.wonders.xlab.patient.module.mydoctor;

import com.wonders.xlab.patient.module.mydoctor.adapter.MyDoctorItemBean;
import com.wonders.xlab.patient.mvp.entity.DoctorMyEntity;
import com.wonders.xlab.patient.mvp.model.DoctorMyModel;
import com.wonders.xlab.patient.mvp.model.DoctorMyModelContract;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import im.hua.library.base.mvp.impl.BasePagePresenter;

/**
 * Created by hua on 16/3/14.
 */
public class DoctorMyPresenter extends BasePagePresenter implements DoctorMyPresenterContract.Actions, DoctorMyModelContract.Callback {
    private DoctorMyPresenterContract.ViewListener mViewListener;
    private DoctorMyModelContract.Actions mDoctorMyModel;

    @Inject
    public DoctorMyPresenter(DoctorMyModel doctorMyModel, DoctorMyPresenterContract.ViewListener viewListener) {
        mDoctorMyModel = doctorMyModel;
        mViewListener = viewListener;
        addModel(mDoctorMyModel);
    }

    @Override
    public void getMyDoctors(String patientId, boolean isRefresh) {
        mViewListener.showLoading("");
        if (isRefresh) {
            resetPageInfo();
        }
        mDoctorMyModel.getMyDoctors(patientId, getNextPageIndex(), DEFAULT_PAGE_SIZE,this);
    }

    @Override
    public void onReceiveMyDoctorListSuccess(DoctorMyEntity.RetValuesEntity valuesEntity) {
        mViewListener.hideLoading();

        updatePageInfo(valuesEntity.getServiceFalse().getPage());

        ArrayList<MyDoctorItemBean> doctorItemBeanArrayList = new ArrayList<>();

        List<DoctorMyEntity.RetValuesEntity.ServiceTrueEntity> inServiceEntityList = valuesEntity.getServiceTrue();
        for (DoctorMyEntity.RetValuesEntity.ServiceTrueEntity entity : inServiceEntityList) {
            MyDoctorItemBean itemBean = new MyDoctorItemBean();
            itemBean.setType(MyDoctorItemBean.TYPE_IN_SERVICE);
            itemBean.setOwnerId(entity.getOwnerId());
            itemBean.setImGroupId(entity.getImGroupId());
            itemBean.setDoctorGroupName(entity.getName());
            itemBean.setLatestChatMessage(entity.getContent());
            itemBean.setTimeStr(entity.getTimeExp());
            itemBean.setPortraitUrl(entity.getAvatars());

            doctorItemBeanArrayList.add(itemBean);
        }

        DoctorMyEntity.RetValuesEntity.ServiceFalseEntity serviceFalseEntityList = valuesEntity.getServiceFalse();
        List<DoctorMyEntity.RetValuesEntity.ServiceFalseEntity.ContentEntity> contentEntityList = serviceFalseEntityList.getContent();
        for (DoctorMyEntity.RetValuesEntity.ServiceFalseEntity.ContentEntity entity : contentEntityList) {
            MyDoctorItemBean itemBean = new MyDoctorItemBean();
            itemBean.setType(MyDoctorItemBean.TYPE_OUT_OF_SERVICE);
            itemBean.setImGroupId(entity.getImGroupId());
            itemBean.setOwnerId(entity.getOwnerId());
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
        showError(mViewListener,code,message);
    }
}

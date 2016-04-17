package com.wonders.xlab.pci.doctor.data.presenter.impl;

import com.wonders.xlab.pci.doctor.module.chatroom.symptom.bean.SymptomBean;
import com.wonders.xlab.pci.doctor.data.entity.SymptomCommentEntity;
import com.wonders.xlab.pci.doctor.data.entity.SymptomEntity;
import com.wonders.xlab.pci.doctor.data.model.impl.SymptomCommentModel;
import com.wonders.xlab.pci.doctor.data.model.impl.SymptomModel;

import java.util.ArrayList;
import java.util.List;

import im.hua.library.base.mvp.impl.BasePresenter;
import im.hua.library.base.mvp.listener.BasePresenterListener;
import im.hua.utils.DateUtil;


/**
 * Created by hua on 16/2/24.
 */
public class SymptomPresenter extends BasePresenter implements SymptomModel.SymptomModelListener, SymptomCommentModel.SymptomCommentModelListener {
    private SymptomPresenterListener mISymptomPresenter;
    private SymptomModel mSymptomModel;
    private SymptomCommentModel mSymptomCommentModel;

    public SymptomPresenter(SymptomPresenterListener ISymptomPresenter) {
        mISymptomPresenter = ISymptomPresenter;
        mSymptomModel = new SymptomModel(this);
        mSymptomCommentModel = new SymptomCommentModel(this);
        addModel(mSymptomModel);
        addModel(mSymptomCommentModel);
    }

    public void getSymptomList(String userId) {
        mSymptomModel.getSymptomList(userId);
    }

    public void saveComment(String symptomId,String doctorId,String comment,boolean check) {
        mSymptomCommentModel.saveComment(symptomId,doctorId,comment,check);
    }

    @Override
    public void onReceiveSymptomSuccess(SymptomEntity entity) {
        if (null == entity.getRet_values()) {
            mISymptomPresenter.showNetworkError("获取数据出错，请重试！");
            return;
        }
        List<SymptomEntity.RetValuesEntity.ContentEntity> contentEntityList = entity.getRet_values().getContent();

        List<SymptomBean> symptomBeanList = new ArrayList<>();
        for (int i = 0; i < contentEntityList.size(); i++) {
            SymptomEntity.RetValuesEntity.ContentEntity contentEntity = contentEntityList.get(i);
            SymptomBean bean = new SymptomBean();
            bean.setComment(contentEntity.getDoctorRemark());
            bean.setSymptomId(contentEntity.getId());
            bean.setIsChecked(contentEntity.isChecked());
            bean.setSymptomStr(contentEntity.getSymptomContent());
            bean.setTimeStr(DateUtil.format(contentEntity.getRecordTime(),"yyyy-MM-dd"));

            symptomBeanList.add(bean);
        }

        mISymptomPresenter.showSymptomList(symptomBeanList);
    }

    @Override
    public void onReceiveFailed(int code, String message) {
        mISymptomPresenter.showNetworkError(message);
    }

    @Override
    public void onReceiveSymptomCommentSuccess(SymptomCommentEntity entity) {
        mISymptomPresenter.saveCommentSuccess();
    }

    public interface SymptomPresenterListener extends BasePresenterListener {
        void showSymptomList(List<SymptomBean> symptomBeanList);

        void saveCommentSuccess();
    }
}
